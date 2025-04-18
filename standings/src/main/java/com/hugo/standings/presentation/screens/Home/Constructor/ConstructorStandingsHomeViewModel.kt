package com.hugo.standings.presentation.screens.Home.Constructor

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.standings.domain.usecase.GetConstructorStandingsUseCase
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConstructorStandingsHomeViewModel @Inject constructor(
    private val getConstructorStandingsUseCase: GetConstructorStandingsUseCase,
): ViewModel() {
    init{
        AppLogger.d(message = "Inside ConstructorStandingsHomeViewModel")
        constructorStandings("2025")
    }

    private val _state = mutableStateOf(ConstructorStandingsHomeUiState())
    val state: MutableState<ConstructorStandingsHomeUiState> = _state

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
}