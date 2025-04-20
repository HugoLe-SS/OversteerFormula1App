package com.hugo.standings.presentation.screens.Home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.standings.domain.usecase.GetConstructorStandingsUseCase
import com.hugo.standings.domain.usecase.GetDriverStandingsUseCase
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StandingsHomeViewModel @Inject constructor(
    private val getConstructorStandingsUseCase: GetConstructorStandingsUseCase,
    private val getDriverStandingsUseCase: GetDriverStandingsUseCase
): ViewModel() {

    private val _state = mutableStateOf(ConstructorStandingsHomeUiState())
    val state: MutableState<ConstructorStandingsHomeUiState> = _state

    init{
        AppLogger.d(message = "Inside StandingsHomeViewModel")
        constructorStandings(season = "current")
//        getDriverStandings(season = "current")
    }



    private fun constructorStandings(season: String){
        getConstructorStandingsUseCase(season = season).onEach{ result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = ConstructorStandingsHomeUiState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ConstructorStandingsHomeUiState(
                        isLoading = false,
                        constructorStandings = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = ConstructorStandingsHomeUiState(
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)

    }

    private fun getDriverStandings(season: String) {
        getDriverStandingsUseCase(season = season).onEach{result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        driverStandings = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
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
                loadStandings()
                AppLogger.d(message = "${state.value.currentType}")
            }

            is ToggleStandingsEvent.SetStandingsType -> {
                _state.value = _state.value.copy(currentType = event.type)
                loadStandings()
            }
        }
    }

}