package com.example.omdbapidemo.data.remote

import com.example.omdbapidemo.data.remote.api.OmdbApiService
import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.data.remote.mapper.MovieDetailMapper
import com.example.omdbapidemo.data.remote.mapper.MovieSearchMapper
import com.example.omdbapidemo.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryDataSource @Inject constructor(
    private val apiService: OmdbApiService
) : NetworkRepository {

    override suspend fun getMovieList(searchString: String): List<Movie> {
        val response = apiService.getMovieListByText(searchString)
        return if (response.response) {
            MovieSearchMapper().fromModel(response)
        } else {
            listOf()
        }
    }

    override suspend fun getMovieById(id: String): MovieDetail {
        val response = apiService.getMovieDetailById(id)
        return MovieDetailMapper().fromModel(response)
    }
}