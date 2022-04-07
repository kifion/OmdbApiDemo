package com.example.omdbapidemo.data.local

import android.content.SharedPreferences
import com.example.omdbapidemo.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
    private val preferences: SharedPreferences
): UserPreferenceRepository {
    companion object {
        private const val LAST_SEARCH_TEXT_REQUEST = "last_search_text_request"
    }

    override suspend fun saveLastTextRequest(searchText: String) {
        preferences.edit().putString(LAST_SEARCH_TEXT_REQUEST, searchText).apply()
    }

    override suspend fun getLastTextRequest(): String {
        return preferences.getString(LAST_SEARCH_TEXT_REQUEST, "") ?: ""
    }
}