package com.hugo.oversteerf1.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.oversteerf1.domain.usecase.GetF1NewsUseCase
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getF1NewsUseCase: GetF1NewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    init{
        AppLogger.d(message = "Inside HomeViewModel")
        getF1News()
    }

    private fun getF1News(){
        AppLogger.d(message = "GetF1News function run without crashing")

        getF1NewsUseCase().onEach { resource ->
            when(resource){
                is Resource.Loading -> {
                    AppLogger.d(message = "Loading state for Home Screen")
                    _state.update {
                        it.copy(
                            isLoading = resource.isFetchingFromNetwork,
                            error = null
                        )
                    }
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            news = resource.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    AppLogger.d(message = "Success for Home Screen ")
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = resource.error,
                            isLoading = false
                        )
                    }
                    AppLogger.e(message = "Error Loading Home Screen")
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.RetryFetch -> {
                getF1News()
            }
        }
    }

}