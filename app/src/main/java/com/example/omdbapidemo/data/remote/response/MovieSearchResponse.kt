package com.example.omdbapidemo.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Response")
    val response: Boolean = false,
    @SerializedName("Search")
    val search: List<Search> = emptyList(),
    @SerializedName("totalResults")
    val totalResults: String  = ""
)

data class Search(
    @SerializedName("imdbID")
    val imdbID: String = "",
    @SerializedName("Poster")
    val poster: String = "",
    @SerializedName("Title")
    val title: String = "",
    @SerializedName("Type")
    val type: String = "",
    @SerializedName("Year")
    val year: String = ""
)