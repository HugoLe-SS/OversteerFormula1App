package com.hugo.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.news.domain.usecase.GetF1NewsUseCase
import com.hugo.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class F1NewsViewModel @Inject constructor(
    private val getF1NewsUseCase: GetF1NewsUseCase
): ViewModel() {
     private val _state =  MutableStateFlow<F1NewsUiState>(F1NewsUiState())
     val state: StateFlow<F1NewsUiState>  = _state

    init {
        getF1News(isRefresh = false)
    }

    private fun getF1News(isRefresh: Boolean) {
        getF1NewsUseCase().onEach { resource ->
            when (resource) {

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = if (isRefresh) it.isLoading else resource.isFetchingFromNetwork,
                            isRefreshing = if (isRefresh) resource.isFetchingFromNetwork else it.isRefreshing,
                            error = null
                        )
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            newsList = resource.data ?: emptyList(),
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = resource.error
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun onRefresh() {
        _state.update {
            it.copy(
                isRefreshing = true,
                newsList = emptyList()
            )
        }
        getF1News(isRefresh = true)
    }

}