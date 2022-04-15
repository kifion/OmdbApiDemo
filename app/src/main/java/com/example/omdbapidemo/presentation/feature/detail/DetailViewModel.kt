package com.example.omdbapidemo.presentation.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.model.Status
import com.example.omdbapidemo.domain.usecase.detail.GetMovieDetailUseCase
import com.example.omdbapidemo.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSearchDetailUseCase: GetMovieDetailUseCase,
) : BaseViewModel() {

    val detail: MutableLiveData<Status<MovieDetail>> = MutableLiveData<Status<MovieDetail>>()

    fun searchById(id: String) {
        viewModelScope.launch {
            getSearchDetailUseCase.invoke(id)
                .collect { response -> detail.value = response }
        }
    }
}