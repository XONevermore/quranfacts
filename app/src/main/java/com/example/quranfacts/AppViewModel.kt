package com.example.quranfacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranfacts.data.FactsDoc
import com.example.quranfacts.data.FactsRepository
import com.example.quranfacts.data.I18n
import com.example.quranfacts.data.Prefs
import com.example.quranfacts.data.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DataState {
    data object Loading : DataState
    data class Ready(val doc: FactsDoc) : DataState
    data class Failed(val message: String?) : DataState
}

class AppViewModel(app: Application) : AndroidViewModel(app) {
    private val prefs = Prefs(app)
    private val repo = FactsRepository(app)

    private val _language = MutableStateFlow(prefs.language)
    val language: StateFlow<String> = _language.asStateFlow()

    /** False until the person has confirmed a language on the first-run screen (see LanguagePickerScreen). */
    private val _languageConfirmed = MutableStateFlow(prefs.languageConfirmed)
    val languageConfirmed: StateFlow<Boolean> = _languageConfirmed.asStateFlow()

    private val _state = MutableStateFlow<DataState>(DataState.Loading)

    /** The facts in the chosen language: facts.json with that language's text overlay applied. */
    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<DataState> = combine(_state, _language) { s, lang -> s to lang }
        .mapLatest { (s, lang) ->
            if (s !is DataState.Ready) s
            else withContext(Dispatchers.Default) { DataState.Ready(I18n.apply(s.doc, I18n.overlay(app, lang))) }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, DataState.Loading)

    private val _arabicSize = MutableStateFlow(prefs.arabicSize)
    val arabicSize: StateFlow<Float> = _arabicSize.asStateFlow()

    private val _theme = MutableStateFlow(prefs.theme)
    val theme: StateFlow<ThemeMode> = _theme.asStateFlow()

    private val _bookmarks = MutableStateFlow(prefs.bookmarks)
    val bookmarks: StateFlow<Set<String>> = _bookmarks.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.value = DataState.Loading
        viewModelScope.launch {
            _state.value = runCatching { repo.load() }
                .fold({ DataState.Ready(it) }, { DataState.Failed(it.message) })
        }
    }

    fun setLanguage(code: String) {
        prefs.language = code
        _language.value = code
    }

    fun confirmLanguage() {
        prefs.languageConfirmed = true
        _languageConfirmed.value = true
    }

    fun setArabicSize(size: Float) {
        _arabicSize.value = size
    }

    fun commitArabicSize() {
        prefs.arabicSize = _arabicSize.value
    }

    fun setTheme(mode: ThemeMode) {
        prefs.theme = mode
        _theme.value = mode
    }

    fun toggleBookmark(id: String) {
        val next = _bookmarks.value.let { if (id in it) it - id else it + id }
        prefs.bookmarks = next
        _bookmarks.value = next
    }
}
