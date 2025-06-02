package com.hugo.result.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.result.domain.usecase.GetConstructorQualifyingResultsUseCase
import com.hugo.result.domain.usecase.GetConstructorRaceResultsUseCase
import com.hugo.result.domain.usecase.GetDriverQualifyingResultsUseCase
import com.hugo.result.domain.usecase.GetDriverRaceResultsUseCase
import com.hugo.result.domain.usecase.GetF1CalendarResultUseCase
import com.hugo.utilities.AppUtilities.formatMillisToTime
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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


    val raceIntervals: StateFlow<List<String?>> = state
        .map { uiState -> calculateIntervals(uiState.f1CalendarResult) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )




    private fun calculateIntervals(results: List<F1CalendarRaceResult>): List<String?> {
        if (results.isEmpty()) {
            return emptyList()
        }

        val leader = results.first()
        val leaderLaps = leader.laps.toIntOrNull()

        return results.mapIndexed { index, current ->
            val currentStatus = current.status.lowercase()

            val statusAbbreviation = when {
                currentStatus.contains("did not start") || currentStatus == "dns" -> "DNS"
                currentStatus.contains("retired") || currentStatus == "dnf" -> "DNF"
                currentStatus.contains("disqualified") || currentStatus == "dsq" -> "DSQ"
                currentStatus.contains("did not qualify") || currentStatus == "dnq" -> "DNQ"
                else -> null
            }
            if (statusAbbreviation != null) {
                return@mapIndexed statusAbbreviation
            }

            if (index == 0) {
                // Leader has no interval to a car ahead.
                return@mapIndexed null
            }

            //Lapped Drivers
            val currentDriverLaps = current.laps.toIntOrNull()
            if (leaderLaps != null && currentDriverLaps != null && currentDriverLaps < leaderLaps) {
                val lapsDown = leaderLaps - currentDriverLaps
                return@mapIndexed "+$lapsDown Lap${if (lapsDown > 1) "s" else ""}"
            }

            if (currentStatus == "lapped" && (leaderLaps == null || currentDriverLaps == null)) {
                return@mapIndexed "Lapped"
            }

            //    calculate interval
            val previousResult = results[index - 1]
            val currentDriverMillis = current.millis?.toLongOrNull()
            val previousDriverMillis = previousResult.millis?.toLongOrNull()

            if (currentDriverMillis != null && previousDriverMillis != null) {
                val previousDriverLaps = previousResult.laps.toIntOrNull()
                if (leaderLaps != null && currentDriverLaps != null && previousDriverLaps != null) {
                    if (currentDriverLaps < previousDriverLaps) {
                        return@mapIndexed current.time
                    }
                }

                val intervalMillis = currentDriverMillis - previousDriverMillis
                if (intervalMillis >= 0) {
                    return@mapIndexed intervalMillis.formatMillisToTime()
                } else {
                    return@mapIndexed current.time
                }
            } else {
                return@mapIndexed current.time
            }
        }
    }



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
        getF1CalendarResultUseCase(season, circuitId).onEach { resource ->
            when (resource) {
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
                            f1CalendarResult = resource.data ?: emptyList()
                        )
                    }
                    AppLogger.d(message = "Success getting calendar results")
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = resource.error
                        )
                    }
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
    }

    private fun getDriverRaceResults(season: String, driverId: String) {
        getDriverRaceResultsUseCase(season = season, driverId = driverId)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        AppLogger.d(message = "DriverDetailsViewModel Loading")
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                driverRaceResults = resource.data ?: emptyList()
                            )
                        }
                        AppLogger.d(message = "Success Getting Driver race results")
                    }

                    is Resource.Error -> {
                        AppLogger.e(message = "DriverDetailsViewModel Error")
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.error
                            )
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    private fun getDriverQualifyingResults(season: String, driverId: String) {
        getDriverQualifyingResultUseCase(season = season, driverId = driverId)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        AppLogger.d(message = "DriverDetailsViewModel Loading")
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                driverQualifyingResults = resource.data ?: emptyList()
                            )
                        }
                        AppLogger.d(message = "Success Getting Driver Qualifying Results")
                    }

                    is Resource.Error -> {
                        AppLogger.e(message = "DriverDetailsViewModel Error")
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = resource.error
                            )
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    private fun getConstructorRaceResults(season: String, constructorId:String){
        getConstructorRaceResultsUseCase(season = season, constructorId = constructorId).onEach { resource ->
            when (resource) {
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
                            constructorRaceResults = resource.data ?: emptyList()
                        )
                    }
                    AppLogger.d(message = "Success ${resource.data?.size}")
                }
                is Resource.Error -> {
                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = resource.error
                        )
                    }
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)

    }

    private fun getConstructorQualifyingResults(season: String, constructorId:String){
        getConstructorQualifyingResultsUseCase(season = season, constructorId = constructorId).onEach { resource ->
            when (resource) {
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
                            constructorQualifyingResults = resource.data ?: emptyList()
                        )
                    }
                    AppLogger.d(message = "Success ${resource.data?.size}")
                }
                is Resource.Error -> {
                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = resource.error
                        )
                    }
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)

    }
}