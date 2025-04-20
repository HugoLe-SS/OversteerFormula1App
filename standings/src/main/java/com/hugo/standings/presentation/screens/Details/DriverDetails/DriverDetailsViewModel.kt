package com.hugo.standings.presentation.screens.Details.DriverDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.standings.domain.usecase.GetDriverQualifyingResultsUseCase
import com.hugo.standings.domain.usecase.GetDriverRaceResultsUseCase
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DriverDetailsViewModel @Inject constructor(
    private val getDriverRaceResultsUseCase: GetDriverRaceResultsUseCase,
    private val getDriverQualifyingResultUseCase: GetDriverQualifyingResultsUseCase
): ViewModel() {

    private val _state = mutableStateOf(DriverDetailsUiState())
    val state: MutableState<DriverDetailsUiState> = _state


    fun fetchDriverDetails(season: String , driverId: String) {
        AppLogger.d(message = "Inside DriverDetailsViewModel")
        getDriverRaceResults(season = season, driverId = driverId)
        getDriverQualifyingResults(season = season, driverId = driverId)
    }

    private fun getDriverRaceResults(season: String, driverId:String){
        getDriverRaceResultsUseCase(season = season, driverId = driverId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    AppLogger.d(message = "DriverDetailsViewModel Loading")
                    _state.value = DriverDetailsUiState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = DriverDetailsUiState(
                        isLoading = false,
                        driverRaceResults = result.data ?: emptyList()
                    )
                    AppLogger.d(message = "Success ${result.data?.size}")
                }
                is Resource.Error -> {
                    AppLogger.e(message = "DriverDetailsViewModel Error")
                    _state.value = DriverDetailsUiState(
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)

    }

    private fun getDriverQualifyingResults(season: String, driverId:String){
        getDriverQualifyingResultUseCase(season = season, driverId = driverId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    AppLogger.d(message = "DriverDetailsViewModel Loading")
                    _state.value = DriverDetailsUiState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        driverQualifyingResults = result.data ?: emptyList()
                    )
                    AppLogger.d(message = "Success ${result.data?.size}")
                }
                is Resource.Error -> {
                    AppLogger.e(message = "DriverDetailsViewModel Error")
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)

    }

}