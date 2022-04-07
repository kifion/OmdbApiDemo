package com.example.omdbapidemo.data.remote.mapper

import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.data.remote.response.MovieDetailResponse

class MovieDetailMapper {
    fun fromModel(detailResponse: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            detailResponse.actors,
            detailResponse.awards,
            detailResponse.country,
            detailResponse.genre,
            detailResponse.poster,
            detailResponse.title,
            detailResponse.year,
            detailResponse.plot,
            detailResponse.imdbRating,
        )
    }
}
