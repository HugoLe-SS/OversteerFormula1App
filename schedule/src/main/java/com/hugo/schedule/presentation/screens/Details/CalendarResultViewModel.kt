package com.hugo.schedule.presentation.screens.Details

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hugo.schedule.domain.usecase.GetF1CalendarResultUseCase
import com.hugo.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalendarResultViewModel @Inject constructor(
    private val getF1CalendarResultUseCase: GetF1CalendarResultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarResultUiState())
    val state: StateFlow<CalendarResultUiState> = _state


    fun fetchF1CalendarResult(season: String, round: String) {
        getF1CalendarResult(season, round)
    }

    private fun getF1CalendarResult(season: String, round: String) {
        getF1CalendarResultUseCase(season, round).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            f1CalendarResult = result.data ?: emptyList()
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
    }
}