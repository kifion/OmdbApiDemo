package com.example.omdbapidemo.presentation.core

data class Response<out T>(
    val status: Status,
    val data: T?,
    val error: String? = ""
) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> loading(): Response<T> {
            return Response(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        fun <T> error(error: String?): Response<T> {
            return Response(Status.ERROR, null, error)
        }
    }
}
