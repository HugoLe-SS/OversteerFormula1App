package com.hugo.standings.presentation.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.standings.domain.usecase.GetConstructorStandingsUseCase
import com.hugo.standings.domain.usecase.GetDriverStandingsUseCase
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
class StandingsHomeViewModel @Inject constructor(
    private val getConstructorStandingsUseCase: GetConstructorStandingsUseCase,
    private val getDriverStandingsUseCase: GetDriverStandingsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(StandingsHomeUiState())
    val state: StateFlow<StandingsHomeUiState> = _state

    init{
        AppLogger.d(message = "Inside StandingsHomeViewModel")
        constructorStandings(season = "current")
//        getDriverStandings(season = "current")
    }



    private fun constructorStandings(season: String){
        getConstructorStandingsUseCase(season = season).onEach{ resource ->
            when(resource){
                is Resource.Loading -> {
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
                            isLoading = false,
                            constructorStandings = resource.data ?: emptyList()
                        )
                    }
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


    private fun getDriverStandings(season: String) {
        getDriverStandingsUseCase(season = season).onEach{result ->
            when(result){
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
                            driverStandings = result.data ?: emptyList()
                        )
                    }
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

    private fun loadStandings() {
        when (_state.value.currentType) {
            StandingsType.CONSTRUCTOR -> constructorStandings(season = "current")
            StandingsType.DRIVER -> getDriverStandings(season = "current")
        }
    }


    fun onEvent(event: ToggleStandingsEvent) {
        when (event) {
            is ToggleStandingsEvent.ToggleStandingsType -> {
                _state.value = _state.value.copy(
                    currentType = when (_state.value.currentType) {
                        StandingsType.CONSTRUCTOR -> StandingsType.DRIVER
                        StandingsType.DRIVER -> StandingsType.CONSTRUCTOR
                    }
                )
                AppLogger.d(message = "${_state.value.currentType}")
            }

            is ToggleStandingsEvent.SetStandingsType -> {
                _state.value = _state.value.copy(currentType = event.type)
                loadStandings()
            }
        }
    }

}