package com.example.omdbapidemo.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name: String,
    val year: String,
    val imdbId: String,
    val poster: String,
) : Parcelable