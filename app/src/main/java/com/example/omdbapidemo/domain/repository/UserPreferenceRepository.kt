package com.example.omdbapidemo.domain.repository

interface UserPreferenceRepository {
    suspend fun saveLastTextRequest(searchText: String)
    suspend fun getLastTextRequest(): String
}