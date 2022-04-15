package com.example.omdbapidemo.presentation.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.domain.model.Status
import com.example.omdbapidemo.domain.usecase.detail.GetMovieListUseCase
import com.example.omdbapidemo.domain.usecase.home.GetLastTextRequest
import com.example.omdbapidemo.domain.usecase.home.SaveLastTextRequest
import com.example.omdbapidemo.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
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

    var searchList = MutableLiveData<Status<List<Movie>>>()
    var lastSearchText = MutableLiveData<String>()

    var queryTextChangedJob: Job? = null

    fun searchByText(text: String) {
        queryTextChangedJob?.cancel()
        queryTextChangedJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            getSearchListUseCase.invoke(text)
                .collect { response ->
                    withContext(Dispatchers.Main) { searchList.value = response }
                }
            saveLastTextRequest.invoke(text)
            lastSearchText.postValue(text)
        }
    }

    private fun setupLastSearchTextRequest() {
        viewModelScope.launch {
            val lastRequest = getLastTextRequest.invoke()
            lastSearchText.postValue(lastRequest)
            searchByText(lastRequest)
        }
    }
}