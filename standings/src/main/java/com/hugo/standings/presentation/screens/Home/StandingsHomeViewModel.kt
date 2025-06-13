package com.hugo.standings.presentation.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.standings.domain.usecase.GetConstructorStandingsUseCase
import com.hugo.standings.domain.usecase.GetDriverStandingsUseCase
import com.hugo.utilities.Resource
import com.hugo.utilities.com.hugo.utilities.AppLaunchManager
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StandingsHomeViewModel @Inject constructor(
    private val getConstructorStandingsUseCase: GetConstructorStandingsUseCase,
    private val getDriverStandingsUseCase: GetDriverStandingsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(StandingsHomeUiState())
    val state: StateFlow<StandingsHomeUiState> = _state

    private var currentDataFetchJob: Job? = null

    init{
        AppLogger.d(message = "Inside StandingsHomeViewModel")
        constructorStandings(season = "current", isRefresh = false)
//        getDriverStandings(season = "current")
    }



    private fun constructorStandings(season: String, isRefresh: Boolean) {
        currentDataFetchJob = getConstructorStandingsUseCase(season = season).onEach{ resource ->
            when(resource){
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
                            constructorStandings = resource.data ?: emptyList()
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
                else -> Unit
            }
        }.launchIn(viewModelScope)

    }


    private fun getDriverStandings(season: String, isRefresh: Boolean) {
        currentDataFetchJob = getDriverStandingsUseCase(season = season).onEach{ resource ->
            when(resource){
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
                            driverStandings = resource.data ?: emptyList(),
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
                else -> Unit
            }

        }.launchIn(viewModelScope)
    }

    private fun loadStandings(isRefresh: Boolean) {
        currentDataFetchJob?.cancel()

        when (_state.value.currentType) {
            StandingsType.CONSTRUCTOR -> constructorStandings(season = "current", isRefresh = isRefresh)
            StandingsType.DRIVER -> getDriverStandings(season = "current", isRefresh = isRefresh)
        }
    }


    fun onEvent(event: StandingsEvent) {
        when (event) {
            is StandingsEvent.ToggleStandingsType -> {
                _state.value = _state.value.copy(
                    currentType = when (_state.value.currentType) {
                        StandingsType.CONSTRUCTOR -> StandingsType.DRIVER
                        StandingsType.DRIVER -> StandingsType.CONSTRUCTOR
                    }
                )
                AppLogger.d(message = "${_state.value.currentType}")
            }

            is StandingsEvent.SetStandingsType -> {
                _state.value = _state.value.copy(currentType = event.type)
                loadStandings(isRefresh = false)
            }

            is StandingsEvent.RetryFetch -> {
                AppLogger.d(message = "Retrying fetch for ${_state.value.currentType}")
                loadStandings(isRefresh = false)
            }

            is StandingsEvent.Refresh -> {
                AppLogger.d(message = "Refreshing standings for ${_state.value.currentType}")
                if ( !_state.value.isLoading) {
                    if(_state.value.currentType == StandingsType.CONSTRUCTOR) {
                        _state.update {
                            it.copy(
                                isRefreshing = true,
                                constructorStandings = emptyList(),
                            )
                        }
                        AppLaunchManager.hasFetchedConstructorStandings = false
                    }
                    else{
                        _state.update {
                            it.copy(
                                isRefreshing = true,
                                driverStandings = emptyList()
                            )
                        }
                        AppLaunchManager.hasFetchedDriverStandings = false
                    }

                    loadStandings(isRefresh = true)
                }
            }
        }
    }

}