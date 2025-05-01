package com.hugo.standings.presentation.screens.Details.DriverDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.standings.domain.usecase.GetDriverDetailsUseCase
import com.hugo.standings.domain.usecase.GetDriverQualifyingResultsUseCase
import com.hugo.standings.domain.usecase.GetDriverRaceResultsUseCase
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
class DriverDetailsViewModel @Inject constructor(
    private val getDriverRaceResultsUseCase: GetDriverRaceResultsUseCase,
    private val getDriverQualifyingResultUseCase: GetDriverQualifyingResultsUseCase,
    private val getDriverDetailsUseCase: GetDriverDetailsUseCase
): ViewModel() {


    private val _state = MutableStateFlow(DriverDetailsUiState())
    val state: StateFlow<DriverDetailsUiState> = _state


    fun fetchDriverDetails(season: String , driverId: String) {
        AppLogger.d(message = "Inside DriverDetailsViewModel")
        getDriverRaceResults(season = season, driverId = driverId)
        getDriverQualifyingResults(season = season, driverId = driverId)
        getDriverDetails(driverId = driverId)
    }

    private fun getDriverRaceResults(season: String, driverId: String) {
        getDriverRaceResultsUseCase(season = season, driverId = driverId)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        AppLogger.d(message = "DriverDetailsViewModel Loading")
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                driverRaceResults = result.data ?: emptyList()
                            )
                        }
                        AppLogger.d(message = "Success Getting Driver race results")
                    }

                    is Resource.Error -> {
                        AppLogger.e(message = "DriverDetailsViewModel Error")
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    else -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getDriverQualifyingResults(season: String, driverId: String) {
        getDriverQualifyingResultUseCase(season = season, driverId = driverId)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        AppLogger.d(message = "DriverDetailsViewModel Loading")
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                driverQualifyingResults = result.data ?: emptyList()
                            )
                        }
                        AppLogger.d(message = "Success Getting Driver Qualifying Results")
                    }

                    is Resource.Error -> {
                        AppLogger.e(message = "DriverDetailsViewModel Error")
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    else -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getDriverDetails(driverId: String){
        getDriverDetailsUseCase(driverId = driverId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    AppLogger.d(message = "DriverDetailsViewModel Loading")
                    _state.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            driverDetails = result.data
                        )
                    }
                    AppLogger.d(message = "Success Getting Driver Details")
                }

                is Resource.Error -> {
                    AppLogger.e(message = "DriverDetailsViewModel Error")
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