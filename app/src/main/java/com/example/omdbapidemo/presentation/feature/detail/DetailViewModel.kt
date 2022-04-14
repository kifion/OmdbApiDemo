package com.example.omdbapidemo.presentation.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.usecase.detail.GetMovieDetailUseCase
import com.example.omdbapidemo.domain.usecase.detail.GetMovieListUseCase
import com.example.omdbapidemo.domain.usecase.home.SaveLastTextRequest
import com.example.omdbapidemo.presentation.core.BaseViewModel
import com.example.omdbapidemo.presentation.core.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSearchDetailUseCase: GetMovieDetailUseCase,
) : BaseViewModel() {

    var detail = MutableLiveData<Response<MovieDetail>>()

    fun searchById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            detail.postValue(Response.loading())
            try {
                val responseSearchList = getSearchDetailUseCase.invoke(GetMovieDetailUseCase.Params(id))
                detail.postValue(Response.success(responseSearchList))
            } catch (exception: Exception) {
                detail.postValue(Response.error(null))
            }
        }
    }
}