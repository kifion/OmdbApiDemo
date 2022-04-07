package com.example.omdbapidemo.domain.repository

import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.model.Movie

interface NetworkRepository {
    suspend fun getMovieList(searchString: String): List<Movie>
    suspend fun getMovieById(id: String): MovieDetail
}