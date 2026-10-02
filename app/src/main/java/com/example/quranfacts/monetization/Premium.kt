package com.example.quranfacts.monetization

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import com.example.quranfacts.BuildConfig
import com.example.quranfacts.data.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.annotation.StringRes
import com.example.quranfacts.R

/**
 * The ad-free subscription, sold through Google Play Billing.
 *
 * One subscription product ([PRODUCT_ID]) is created in the Play Console; every base plan and offer on it
 * (monthly, yearly, a free trial...) appears in the app with the price Google Play shows the user. Owning an
 * active subscription removes all ads; everything else in the app stays free for everyone.
 */
object Premium {
    /** The subscription's product ID in the Play Console (Monetize > Subscriptions). */
    const val PRODUCT_ID = "premium"
    private const val TAG = "QF-billing"

    enum class Status { Connecting, Ready, Unavailable }

    /** One choice on the paywall: a base plan, with the best offer (for example a free trial) the user can get. */
    data class Plan(
        val basePlanId: String,
        val offerToken: String,
        /** Recurring price, already formatted in the user's currency by Google Play, e.g. "$2.99". */
        val price: String,
        val priceMicros: Long,
        /** ISO 8601 billing period of the recurring price, e.g. "P1M". */
        val period: String,
        /** Length of a free trial, e.g. "P1W", or null. */
        val freeTrial: String? = null,
        /** Percentage saved against the shortest plan, e.g. 44, or null. */
        val savingPercent: Int? = null,
    )

    data class State(
        /** True while the user owns an active subscription: no ads anywhere. */
        val active: Boolean = false,
        val status: Status = Status.Connecting,
        val plans: List<Plan> = emptyList(),
        /** A purchase is waiting for payment (for example cash at a shop); ads go once it clears. */
        val pending: Boolean = false,
        /** The last error worth telling the user about, if any. */
        @StringRes val message: Int? = null,
    )

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var client: BillingClient? = null
    private var prefs: Prefs? = null
    private val details = mutableMapOf<String, ProductDetails>()
    private var demo = false

    /** Call once from Application.onCreate. */
    fun init(context: Context) {
        if (client != null) return
        val app = context.applicationContext
        prefs = Prefs(app).also { _state.value = State(active = it.premium) }
        client = BillingClient.newBuilder(app)
            .setListener { result, purchases -> onPurchasesUpdated(result, purchases) }
            .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
            .enableAutoServiceReconnection()
            .build()
        connect()
    }

    private fun connect() {
        val c = client ?: return
        _state.update { it.copy(status = Status.Connecting, message = null) }
        c.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingResponseCode.OK) {
                    scope.launch {
                        refresh()
                        loadPlans()
                    }
                } else {
                    Log.w(TAG, "Billing setup failed: ${result.responseCode} ${result.debugMessage}")
                    _state.update { if (demo) it else it.copy(status = Status.Unavailable) }
                }
            }

            // With automatic reconnection on, the library reconnects by itself before the next call.
            override fun onBillingServiceDisconnected() = Unit
        })
    }

    /** Try again after "unavailable", for example when the user signs in to Google Play. */
    fun retry() {
        if (client?.isReady == true) scope.launch { refresh(); loadPlans() } else connect()
    }

    /**
     * Asks Google Play what the user owns. Call on every resume: a subscription can start, renew, be paused or
     * be cancelled outside the app.
     */
    fun refreshAsync() {
        if (client?.isReady == true) scope.launch { refresh() }
    }

    private suspend fun refresh() {
        val c = client ?: return
        val result = c.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(ProductType.SUBS).build())
        if (result.billingResult.responseCode == BillingResponseCode.OK) {
            handle(result.purchasesList, complete = true)
        } else {
            Log.w(TAG, "queryPurchases failed: ${result.billingResult.debugMessage}")
        }
    }

    private suspend fun loadPlans() {
        val c = client ?: return
        val product = QueryProductDetailsParams.Product.newBuilder()
            .setProductId(PRODUCT_ID)
            .setProductType(ProductType.SUBS)
            .build()
        val result = c.queryProductDetails(QueryProductDetailsParams.newBuilder().setProductList(listOf(product)).build())
        val subscription = result.productDetailsList.orEmpty().firstOrNull { it.productId == PRODUCT_ID }
        if (result.billingResult.responseCode != BillingResponseCode.OK || subscription == null) {
            Log.w(TAG, "No subscription '$PRODUCT_ID' in Google Play: ${result.billingResult.debugMessage}")
            _state.update { if (demo) it else it.copy(status = Status.Unavailable, plans = emptyList()) }
            return
        }
        details[PRODUCT_ID] = subscription
        _state.update { it.copy(status = Status.Ready, plans = plansOf(subscription)) }
    }

    /** One plan per base plan, using its best offer: a free trial if the user is eligible, otherwise the base price. */
    private fun plansOf(product: ProductDetails): List<Plan> {
        val plans = product.subscriptionOfferDetails.orEmpty()
            .groupBy { it.basePlanId }
            .mapNotNull { (basePlanId, offers) ->
                val best = offers.minByOrNull { o -> o.pricingPhases.pricingPhaseList.firstOrNull()?.priceAmountMicros ?: Long.MAX_VALUE }
                    ?: return@mapNotNull null
                val phases = best.pricingPhases.pricingPhaseList
                val recurring = phases.lastOrNull() ?: return@mapNotNull null
                val trial = phases.firstOrNull { it.priceAmountMicros == 0L && it != recurring }
                Plan(
                    basePlanId = basePlanId,
                    offerToken = best.offerToken,
                    price = recurring.formattedPrice,
                    priceMicros = recurring.priceAmountMicros,
                    period = recurring.billingPeriod,
                    freeTrial = trial?.billingPeriod,
                )
            }
            .sortedBy { PlanMath.months(it.period) }
        val shortest = plans.firstOrNull() ?: return plans
        return plans.map {
            it.copy(savingPercent = PlanMath.savingPercent(it.priceMicros, it.period, shortest.priceMicros, shortest.period))
        }
    }

    /**
     * Debug builds only: fills the paywall with made-up plans, to preview it before the subscription exists in the
     * Play Console. `adb shell am start -n com.example.quranfacts/.MainActivity --ez debugDemoPlans true`
     */
    fun showDemoPlans() {
        if (!BuildConfig.DEBUG) return
        demo = true
        _state.update {
            it.copy(
                status = Status.Ready,
                plans = listOf(
                    Plan("monthly", DEMO_TOKEN, "$2.99", 2_990_000, "P1M"),
                    Plan("yearly", DEMO_TOKEN, "$19.99", 19_990_000, "P1Y", freeTrial = "P1W", savingPercent = 44),
                ),
            )
        }
    }

    private const val DEMO_TOKEN = "demo"

    /** Opens Google Play's purchase sheet for [plan]. The result arrives in [onPurchasesUpdated]. */
    fun purchase(activity: Activity, plan: Plan) {
        if (plan.offerToken == DEMO_TOKEN) {
            _state.update { it.copy(message = R.string.msg_demo_plans) }
            return
        }
        val c = client ?: return
        val product = details[PRODUCT_ID] ?: return
        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(product)
                        .setOfferToken(plan.offerToken)
                        .build(),
                ),
            )
            .build()
        val result = c.launchBillingFlow(activity, params)
        if (result.responseCode != BillingResponseCode.OK) {
            _state.update { it.copy(message = R.string.msg_purchase_start) }
        }
    }

    private fun onPurchasesUpdated(result: BillingResult, purchases: List<Purchase>?) {
        when (result.responseCode) {
            BillingResponseCode.OK -> scope.launch { handle(purchases.orEmpty(), complete = false) }
            BillingResponseCode.USER_CANCELED -> Unit
            BillingResponseCode.ITEM_ALREADY_OWNED -> scope.launch { refresh() }
            else -> {
                Log.w(TAG, "Purchase failed: ${result.responseCode} ${result.debugMessage}")
                _state.update { it.copy(message = R.string.msg_purchase_failed) }
            }
        }
    }

    /**
     * Grants or removes ad-free access from what Google Play reports. Every new purchase must be acknowledged
     * within three days or Google refunds it. A suspended subscription (payment failed) does not count.
     * [complete] is true when [purchases] is the full list of what the user owns, so absence means "not subscribed".
     */
    private suspend fun handle(purchases: List<Purchase>, complete: Boolean) {
        val c = client ?: return
        val mine = purchases.filter { PRODUCT_ID in it.products }
        val owned = mine.filter { it.purchaseState == Purchase.PurchaseState.PURCHASED && !it.isSuspended }
        owned.filterNot { it.isAcknowledged }.forEach { p ->
            val ack = c.acknowledgePurchase(AcknowledgePurchaseParams.newBuilder().setPurchaseToken(p.purchaseToken).build())
            if (ack.responseCode != BillingResponseCode.OK) Log.w(TAG, "Acknowledge failed: ${ack.debugMessage}")
        }
        val active = owned.isNotEmpty() || (!complete && _state.value.active)
        prefs?.premium = active
        _state.update {
            it.copy(
                active = active,
                pending = mine.any { p -> p.purchaseState == Purchase.PurchaseState.PENDING },
                message = null,
            )
        }
    }

    fun clearMessage() = _state.update { it.copy(message = null) }

    /** Google Play's page where the user can change plan or cancel. */
    fun manageUrl(context: Context): String =
        "https://play.google.com/store/account/subscriptions?sku=$PRODUCT_ID&package=${context.packageName}"
}
