package com.example.omdbapidemo.domain.usecase.home

import com.example.omdbapidemo.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class GetLastTextRequest @Inject constructor(
    private val repository: UserPreferenceRepository
) {
    suspend operator fun invoke(): String {
        return repository.getLastTextRequest()
    }
}