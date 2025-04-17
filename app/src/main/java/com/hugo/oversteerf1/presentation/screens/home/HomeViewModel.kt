package com.hugo.oversteerf1.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.oversteerf1.domain.usecase.GetF1NewsUseCase
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getF1NewsUseCase: GetF1NewsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeUiState())
    val state: MutableState<HomeUiState> = _state

    init{
        AppLogger.d(message = "Inside HomeViewModel")
        getF1News()
    }

    private fun getF1News(){
        AppLogger.d(message = "GetF1News function run without crashing")

        getF1NewsUseCase().onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = HomeUiState(
                        isLoading = true
                    )
                    AppLogger.d(message = "Loading state for Home Screen")
                }
                is Resource.Success -> {
                    _state.value = HomeUiState(
                        news = result.data,
                        isLoading = false
                    )
                    AppLogger.d(message = "Success for Home Screen ")
                }
                is Resource.Error -> {
                    _state.value = HomeUiState(
                        error = result.message,
                        isLoading = false
                    )
                    AppLogger.e(message = "Error Loading Home Screen")
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
    }

}