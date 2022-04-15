package com.example.omdbapidemo.data.remote

import com.example.omdbapidemo.data.remote.api.OmdbApiService
import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.data.remote.mapper.MovieDetailMapper
import com.example.omdbapidemo.data.remote.mapper.MovieSearchMapper
import com.example.omdbapidemo.domain.model.Status
import com.example.omdbapidemo.domain.repository.NetworkRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@InternalCoroutinesApi
class NetworkRepositoryDataSource @Inject constructor(
    private val apiService: OmdbApiService
) : NetworkRepository {

    override suspend fun getMovieList(searchString: String): Flow<Status<List<Movie>>> = flow {
        try {
            emit(Status.Loading)
            val response = apiService.getMovieListByText(searchString)
            emit(Status.Success(MovieSearchMapper().fromModel(response)))
        } catch (e: Exception) {
            emit(Status.Error(e.toString()))
        }
    }

    override suspend fun getMovieById(id: String): Flow<Status<MovieDetail>> = flow {
        try {
            emit(Status.Loading)
            val response = apiService.getMovieDetailById(id)
            emit(Status.Success(MovieDetailMapper().fromModel(response)))
        } catch (e: Exception) {
            emit(Status.Error(e.toString()))
        }
    }
}