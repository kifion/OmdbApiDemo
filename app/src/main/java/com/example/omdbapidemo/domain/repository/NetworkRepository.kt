package com.example.omdbapidemo.domain.repository

import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.domain.model.Status
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun getMovieList(searchString: String):  Flow<Status<List<Movie>>>
    suspend fun getMovieById(id: String): Flow<Status<MovieDetail>>
}