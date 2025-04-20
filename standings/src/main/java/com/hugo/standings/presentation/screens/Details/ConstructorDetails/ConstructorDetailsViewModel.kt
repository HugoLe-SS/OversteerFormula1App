package com.hugo.standings.presentation.screens.Details.ConstructorDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.standings.domain.usecase.GetConstructorQualifyingResultsUseCase
import com.hugo.standings.domain.usecase.GetConstructorRaceResultsUseCase
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConstructorDetailsViewModel @Inject constructor(
    private val getConstructorRaceResultsUseCase: GetConstructorRaceResultsUseCase,
    private val getConstructorQualifyingResultsUseCase: GetConstructorQualifyingResultsUseCase
): ViewModel() {

    private val _state = mutableStateOf(ConstructorDetailsUIState())
    val state: MutableState<ConstructorDetailsUIState> = _state

    fun fetchConstructorDetails(season: String , constructorId: String) {
        AppLogger.d(message = "Inside ConstructorDetailsViewModel")
        getConstructorRaceResults(season = season, constructorId = constructorId)
        getConstructorQualifyingResults(season = season, constructorId = constructorId)
    }

    private fun getConstructorRaceResults(season: String, constructorId:String){
        getConstructorRaceResultsUseCase(season = season, constructorId = constructorId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    AppLogger.d(message = "ConstructorDetailsViewModel Loading")
                    _state.value = ConstructorDetailsUIState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = ConstructorDetailsUIState(
                        isLoading = false,
                        constructorRaceResults = result.data ?: emptyList()
                    )
                    AppLogger.d(message = "Success ${result.data?.size}")
                }
                is Resource.Error -> {
                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
                    _state.value = ConstructorDetailsUIState(
                        isLoading = false,
                        error = result.message
                    )
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
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        constructorQualifyingResults = result.data ?: emptyList()
                    )
                    AppLogger.d(message = "Success ${result.data?.size}")
                }
                is Resource.Error -> {
                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
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