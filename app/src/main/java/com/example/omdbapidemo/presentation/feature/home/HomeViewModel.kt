package com.example.omdbapidemo.presentation.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbapidemo.presentation.core.Event
import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.domain.usecase.detail.GetMovieListUseCase
import com.example.omdbapidemo.domain.usecase.home.GetLastTextRequest
import com.example.omdbapidemo.domain.usecase.home.SaveLastTextRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSearchListUseCase: GetMovieListUseCase,
    private val saveLastTextRequest: SaveLastTextRequest,
    private val getLastTextRequest: GetLastTextRequest,
) : ViewModel() {

    var searchList = MutableLiveData<Event<List<Movie>>>()
    var searchText = MutableLiveData<String>()

    var queryTextChangedJob: Job? = null

    fun searchByText(text: String) {
        queryTextChangedJob?.cancel()

        queryTextChangedJob = this.viewModelScope.launch {
            delay(1000)
            searchList.postValue(Event.loading())
            try {
                val responseSearchList = getSearchListUseCase.invoke(GetMovieListUseCase.Params(text))
                saveLastTextRequest.invoke(SaveLastTextRequest.Params(text))
                searchList.postValue(Event.success(responseSearchList))
            } catch (exception: Exception) {
                searchList.postValue(Event.error("Connection problem"))
            }
        }
    }

    fun setupLastSearchTextRequest() {
        this.viewModelScope.launch {
            val lastRequest = getLastTextRequest.invoke()
            searchText.postValue(lastRequest)
            searchByText(lastRequest)
        }
    }
}