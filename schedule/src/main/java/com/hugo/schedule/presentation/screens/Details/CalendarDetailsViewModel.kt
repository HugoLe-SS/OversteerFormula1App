package com.hugo.schedule.presentation.screens.Details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getF1CircuitInfoUseCase: GetF1CircuitInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarDetailsUiState())
    val state: StateFlow<CalendarDetailsUiState> = _state


    fun fetchCircuitDetails(circuitId: String) {
        getCircuitDetails(circuitId)

    }


    private fun getCircuitDetails(circuitId: String){
        getF1CircuitInfoUseCase(circuitId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = result.isFetchingFromNetwork,
                            error = null
                        )
                    }
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            f1CircuitDetails = result.data,
                            error = null
                        )
                    }
                    AppLogger.d(message = "Success getting circuit Details")
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.error
                        )
                    }
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: CalendarDetailsEvent) {
        when (event) {
            is CalendarDetailsEvent.RetryFetch -> { // Assume RetryFetch carries circuitId now
                AppLogger.d(message = "VM: RetryFetch for ${event.circuitId}")
                fetchCircuitDetails(circuitId = event.circuitId)
            }
        }
    }

}