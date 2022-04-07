package com.example.omdbapidemo.data.remote.mapper

import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.data.remote.response.SearchResponse

class MovieSearchMapper {
    fun fromModel(type: SearchResponse): List<Movie> {
        return type.search.map {
            Movie(
                it.title,
                it.year,
                it.imdbID,
                it.poster
            )
        }
    }
}