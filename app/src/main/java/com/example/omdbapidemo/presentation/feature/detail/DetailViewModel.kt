package com.example.omdbapidemo.presentation.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.usecase.detail.GetMovieDetailUseCase
import com.example.omdbapidemo.presentation.core.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSearchDetailUseCase: GetMovieDetailUseCase,
) : ViewModel() {

    var detail = MutableLiveData<Event<MovieDetail>>()

    fun searchById(id: String) {
        this.viewModelScope.launch {
            detail.postValue(Event.loading())
            val responseDetail = getSearchDetailUseCase.invoke(GetMovieDetailUseCase.Params(id))
            try {
                detail.postValue(Event.success(responseDetail))
            } catch (exception: Exception) {
                detail.postValue(Event.error("Connection problem"))
            }
        }
    }
}