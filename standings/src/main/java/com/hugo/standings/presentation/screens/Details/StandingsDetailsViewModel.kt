package com.hugo.standings.presentation.screens.Details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.standings.domain.usecase.GetConstructorDetailsUseCase
import com.hugo.standings.domain.usecase.GetDriverDetailsUseCase
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
class StandingsDetailsViewModel @Inject constructor(
    private val getDriverDetailsUseCase: GetDriverDetailsUseCase,
    private  val getConstructorDetailsUseCase: GetConstructorDetailsUseCase
): ViewModel() {


    private val _state = MutableStateFlow(StandingsDetailsUIState())
    val state: StateFlow<StandingsDetailsUIState> = _state


    fun fetchDriverDetails(season: String , driverId: String) {
        AppLogger.d(message = "Inside DriverDetailsViewModel")
        getDriverDetails(driverId = driverId)
    }

    fun fetchConstructorDetails(season: String , constructorId: String) {
        AppLogger.d(message = "Inside ConstructorDetailsViewModel")
        getConstructorDetails(constructorId = constructorId)
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

    private fun getConstructorDetails(constructorId: String){
        getConstructorDetailsUseCase(constructorId).onEach { result ->
            when(result){
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
                            constructorDetails = result.data
                        )
                    }
                    AppLogger.d(message = "Success getting constructor details")
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

