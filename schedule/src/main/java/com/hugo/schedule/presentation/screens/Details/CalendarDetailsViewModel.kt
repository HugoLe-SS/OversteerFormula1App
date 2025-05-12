package com.hugo.schedule.presentation.screens.Details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.schedule.domain.usecase.GetF1CalendarResultUseCase
import com.hugo.schedule.domain.usecase.GetF1CircuitInfoUseCase
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
class CalendarDetailsViewModel @Inject constructor(
    private val getF1CalendarResultUseCase: GetF1CalendarResultUseCase,
    private val getF1CircuitInfoUseCase: GetF1CircuitInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarDetailsUiState())
    val state: StateFlow<CalendarDetailsUiState> = _state


    fun fetchF1CalendarResult(season: String, circuitId: String) {
        getF1CalendarResult(season, circuitId)
    }

    fun fetchCircuitDetails(circuitId: String) {
        getCircuitDetails(circuitId)
    }

    private fun getF1CalendarResult(season: String, circuitId: String) {
        getF1CalendarResultUseCase(season, circuitId).onEach { result ->
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
                    AppLogger.d(message = "Success getting calendar results")
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

    private fun getCircuitDetails(circuitId: String){
        getF1CircuitInfoUseCase(circuitId).onEach { result ->
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
                            f1CircuitDetails = result.data
                        )
                    }
                    AppLogger.d(message = "Success getting circuit Details")
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