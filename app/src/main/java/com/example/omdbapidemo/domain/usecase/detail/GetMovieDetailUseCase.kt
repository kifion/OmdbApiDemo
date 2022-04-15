package com.example.omdbapidemo.domain.usecase.detail

import com.example.omdbapidemo.domain.repository.NetworkRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke(word: String) = repository.getMovieById(word)
}