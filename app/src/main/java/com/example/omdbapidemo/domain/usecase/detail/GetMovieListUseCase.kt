package com.example.omdbapidemo.domain.usecase.detail

import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.domain.repository.NetworkRepository
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    data class Params(val word: String)

    suspend operator fun invoke(params: Params): List<Movie> {
        return repository.getMovieList(params.word)
    }
}