package com.hugo.result.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.result.domain.usecase.GetConstructorQualifyingResultsUseCase
import com.hugo.result.domain.usecase.GetConstructorRaceResultsUseCase
import com.hugo.result.domain.usecase.GetDriverQualifyingResultsUseCase
import com.hugo.result.domain.usecase.GetDriverRaceResultsUseCase
import com.hugo.result.domain.usecase.GetF1CalendarResultUseCase
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
class ResultViewModel @Inject constructor(
    private val getDriverRaceResultsUseCase: GetDriverRaceResultsUseCase,
    private val getDriverQualifyingResultUseCase: GetDriverQualifyingResultsUseCase,
    private val getConstructorRaceResultsUseCase: GetConstructorRaceResultsUseCase,
    private val getConstructorQualifyingResultsUseCase: GetConstructorQualifyingResultsUseCase,
    private val getF1CalendarResultUseCase: GetF1CalendarResultUseCase
): ViewModel() {
    private val _state = MutableStateFlow(ResultUIState())
    val state: StateFlow<ResultUIState> = _state

    fun fetchDriverRaceResults(season: String, driverId: String) {
        AppLogger.d(message = "Inside ResultViewModel")
        getDriverRaceResults(season = season, driverId = driverId)
        //getDriverQualifyingResults(season = season, driverId = driverId)
    }

    fun fetchConstructorRaceResults(season: String, constructorId: String) {
        AppLogger.d(message = "Inside ResultViewModel")
        getConstructorRaceResults(season = season, constructorId = constructorId)
        //getConstructorQualifyingResults(season = season, constructorId = constructorId)
    }

    fun fetchF1CalendarResult(season: String, circuitId: String) {
        getF1CalendarResult(season, circuitId)
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
            }.launchIn(viewModelScope)
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
            }.launchIn(viewModelScope)
    }

    private fun getConstructorRaceResults(season: String, constructorId:String){
        getConstructorRaceResultsUseCase(season = season, constructorId = constructorId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    AppLogger.d(message = "ConstructorDetailsViewModel Loading")
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            constructorRaceResults = result.data ?: emptyList()
                        )
                    }
                    AppLogger.d(message = "Success ${result.data?.size}")
                }
                is Resource.Error -> {
                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
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

    private fun getConstructorQualifyingResults(season: String, constructorId:String){
        getConstructorQualifyingResultsUseCase(season = season, constructorId = constructorId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    AppLogger.d(message = "ConstructorDetailsViewModel Loading")
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            constructorQualifyingResults = result.data ?: emptyList()
                        )
                    }
                    AppLogger.d(message = "Success ${result.data?.size}")
                }
                is Resource.Error -> {
                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
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