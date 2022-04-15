package com.example.omdbapidemo.domain.usecase.home

import com.example.omdbapidemo.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class SaveLastTextRequest @Inject constructor(
    private val repository: UserPreferenceRepository
) {
    suspend operator fun invoke(word: String) {
        return repository.saveLastTextRequest(word)
    }
}