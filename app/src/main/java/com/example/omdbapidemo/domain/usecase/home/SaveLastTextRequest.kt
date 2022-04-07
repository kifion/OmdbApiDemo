package com.example.omdbapidemo.domain.usecase.home

import com.example.omdbapidemo.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class SaveLastTextRequest @Inject constructor(
    private val repository: UserPreferenceRepository
) {
    data class Params(val word: String)

    suspend operator fun invoke(params: Params) {
        return repository.saveLastTextRequest(params.word)
    }
}