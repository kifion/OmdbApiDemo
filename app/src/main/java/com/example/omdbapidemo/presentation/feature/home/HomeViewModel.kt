package com.example.omdbapidemo.presentation.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.domain.usecase.detail.GetMovieListUseCase
import com.example.omdbapidemo.domain.usecase.home.GetLastTextRequest
import com.example.omdbapidemo.domain.usecase.home.SaveLastTextRequest
import com.example.omdbapidemo.presentation.core.BaseViewModel
import com.example.omdbapidemo.presentation.core.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSearchListUseCase: GetMovieListUseCase,
    private val saveLastTextRequest: SaveLastTextRequest,
    private val getLastTextRequest: GetLastTextRequest,
) : BaseViewModel() {

    init {
        setupLastSearchTextRequest()
    }

    var searchList = MutableLiveData<Response<List<Movie>>>()
    var lastSearchText = MutableLiveData<String>()

    var queryTextChangedJob: Job? = null

    fun searchByText(text: String) {
        queryTextChangedJob?.cancel()
        queryTextChangedJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            searchList.postValue(Response.loading())
            try {
                val responseSearchList = getSearchListUseCase.invoke(GetMovieListUseCase.Params(text))
                saveLastTextRequest.invoke(SaveLastTextRequest.Params(text))
                searchList.postValue(Response.success(responseSearchList))
            } catch (exception: Exception) {
                searchList.postValue(Response.error(null))
            }
        }
    }

    private fun setupLastSearchTextRequest() {
        this.viewModelScope.launch(Dispatchers.IO) {
            val lastRequest = getLastTextRequest.invoke()
            lastSearchText.postValue(lastRequest)
            searchByText(lastRequest)
        }
    }
}