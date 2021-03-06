package com.example.omdbapidemo.presentation.feature.detail

import androidx.lifecycle.viewModelScope
import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.model.Status
import com.example.omdbapidemo.domain.usecase.detail.GetMovieDetailUseCase
import com.example.omdbapidemo.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSearchDetailUseCase: GetMovieDetailUseCase,
) : BaseViewModel() {

    val detail: MutableStateFlow<Status<MovieDetail>> = MutableStateFlow(Status.Empty)

    fun searchById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getSearchDetailUseCase.invoke(id)
                .collect { response -> withContext(Dispatchers.Main) { detail.value = response } }
        }
    }
}