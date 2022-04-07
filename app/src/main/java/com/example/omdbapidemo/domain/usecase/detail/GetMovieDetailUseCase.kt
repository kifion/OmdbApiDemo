package com.example.omdbapidemo.domain.usecase.detail

import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.repository.NetworkRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    data class Params(val word: String)

    suspend operator fun invoke(params: Params): MovieDetail {
        return repository.getMovieById(params.word)
    }
}