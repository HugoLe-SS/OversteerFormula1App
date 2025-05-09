//package com.hugo.standings.presentation.screens.Details.ConstructorDetails
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.hugo.standings.domain.usecase.GetConstructorDetailsUseCase
//import com.hugo.result.domain.usecase.GetConstructorQualifyingResultsUseCase
//import com.hugo.result.domain.usecase.GetConstructorRaceResultsUseCase
//import com.hugo.utilities.Resource
//import com.hugo.utilities.logging.AppLogger
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.flow.update
//import javax.inject.Inject
//
//@HiltViewModel
//class ConstructorDetailsViewModel @Inject constructor(
//    private val getConstructorRaceResultsUseCase: com.hugo.result.domain.usecase.GetConstructorRaceResultsUseCase,
//    private val getConstructorQualifyingResultsUseCase: com.hugo.result.domain.usecase.GetConstructorQualifyingResultsUseCase,
//    private  val getConstructorDetailsUseCase: GetConstructorDetailsUseCase
//): ViewModel() {
//
//
//    private val _state = MutableStateFlow(ConstructorDetailsUIState())
//    val state: StateFlow<ConstructorDetailsUIState> = _state
//
//    fun fetchConstructorDetails(season: String , constructorId: String) {
//        AppLogger.d(message = "Inside ConstructorDetailsViewModel")
//        getConstructorRaceResults(season = season, constructorId = constructorId)
//        getConstructorQualifyingResults(season = season, constructorId = constructorId)
//        getConstructorDetails(constructorId = constructorId)
//    }
//
//    private fun getConstructorRaceResults(season: String, constructorId:String){
//        getConstructorRaceResultsUseCase(season = season, constructorId = constructorId).onEach { result ->
//            when (result) {
//                is Resource.Loading -> {
//                    AppLogger.d(message = "ConstructorDetailsViewModel Loading")
//                    _state.update {
//                        it.copy(
//                            isLoading = true
//                        )
//                    }
//                }
//                is Resource.Success -> {
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            constructorRaceResults = result.data ?: emptyList()
//                        )
//                    }
//                    AppLogger.d(message = "Success ${result.data?.size}")
//                }
//                is Resource.Error -> {
//                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            error = result.message
//                        )
//                    }
//                }
//                else -> Unit
//            }
//        }.launchIn(viewModelScope)
//
//    }
//
//    private fun getConstructorQualifyingResults(season: String, constructorId:String){
//        getConstructorQualifyingResultsUseCase(season = season, constructorId = constructorId).onEach { result ->
//            when (result) {
//                is Resource.Loading -> {
//                    AppLogger.d(message = "ConstructorDetailsViewModel Loading")
//                    _state.update {
//                        it.copy(
//                            isLoading = true
//                        )
//                    }
//                }
//                is Resource.Success -> {
//                    _state.update {
//                       it.copy(
//                            isLoading = false,
//                            constructorQualifyingResults = result.data ?: emptyList()
//                        )
//                    }
//                    AppLogger.d(message = "Success ${result.data?.size}")
//                }
//                is Resource.Error -> {
//                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            error = result.message
//                        )
//                    }
//                }
//                else -> Unit
//            }
//        }.launchIn(viewModelScope)
//
//    }
//
//    private fun getConstructorDetails(constructorId: String){
//        getConstructorDetailsUseCase(constructorId).onEach { result ->
//            when(result){
//                is Resource.Loading -> {
//                    AppLogger.d(message = "ConstructorDetailsViewModel Loading")
//                    _state.update {
//                        it.copy(
//                            isLoading = true
//                        )
//                    }
//                }
//
//                is Resource.Success -> {
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            constructorDetails = result.data
//                        )
//                    }
//                    AppLogger.d(message = "Success getting constructor details")
//                }
//
//                is Resource.Error -> {
//                    AppLogger.e(message = "ConstructorDetailsViewModel Error")
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            error = result.message
//                        )
//                    }
//                }
//
//                else -> Unit
//            }
//        }.launchIn(viewModelScope)
//    }
//
//}