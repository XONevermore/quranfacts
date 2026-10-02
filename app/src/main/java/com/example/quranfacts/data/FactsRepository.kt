package com.example.quranfacts.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class FactsRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun load(): FactsDoc = withContext(Dispatchers.IO) {
        context.assets.open(ASSET).bufferedReader(Charsets.UTF_8).use {
            json.decodeFromString(FactsDoc.serializer(), it.readText())
        }
    }

    companion object {
        const val ASSET = "facts.json"
    }
}
