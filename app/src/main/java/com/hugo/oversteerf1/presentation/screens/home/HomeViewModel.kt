package com.hugo.oversteerf1.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.datasource.local.entity.F1HomeDetails
import com.hugo.oversteerf1.domain.usecase.GetF1HomeDetailsUseCase
import com.hugo.oversteerf1.domain.usecase.GetF1NewsUseCase
import com.hugo.utilities.AppUtilities
import com.hugo.utilities.AppUtilities.getNextUpcomingSession
import com.hugo.utilities.Resource
import com.hugo.utilities.com.hugo.utilities.Navigation.model.CountDownInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.Session
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getF1NewsUseCase: GetF1NewsUseCase,
    private val getF1HomeDetailsUseCase: GetF1HomeDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    private val _countdown = MutableStateFlow<CountDownInfo?>(null)
    val countdown: StateFlow<CountDownInfo?> = _countdown

    private fun startCountdown(session: Session?) {
        viewModelScope.launch {
            if (session == null) {
                _countdown.value = null
                return@launch
            }

            val sessionStartMillis = AppUtilities.convertToMillis(session.date ?: "", session.time ?: "")
            val sessionEndMillis = sessionStartMillis + (session.sessionDuration.times(60 * 1000))

            while (true) {
                val now = System.currentTimeMillis()

                if (now >= sessionEndMillis) {
                    AppLogger.d(message = "Session ${session?.name} ended. Fetching new home details for next event.")
                    getF1HomeDetails() // Re-fetch to find the actual next event's details
                    _countdown.value = null // Clear current countdown while fetching new details
                    break // Exit this countdown loop
                }


                _countdown.value = AppUtilities.formatToDaysHoursMinutes(
                    session.name,
                    session.date,
                    session.time,
                    session.sessionDuration
                )

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = now

                calendar.add(Calendar.MINUTE, 1)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                val nextMinuteStartMillis = calendar.timeInMillis
                val delayDuration = nextMinuteStartMillis - now

                if (delayDuration > 0) {
                    delay(delayDuration)
                } else {
                    delay(100L)
                }
            }
        }
    }

    fun updateCountdownFromSessions(homeDetails: F1HomeDetails) {
        AppLogger.d(message = "VM updateCountdown: Received homeDetails for raceDate='${homeDetails.raceDate}'")

        val sessions = listOf(
            Session(name = "Main Race", date = homeDetails.raceDate, time = homeDetails.raceTime, sessionDuration = 120),
        )

        val next = getNextUpcomingSession(sessions)
        //_nextSession.value = next

        AppLogger.d(message = "VM updateCountdown: Next session identified: $next") // Log the 'next' session

        startCountdown(next)
    }

    init{
        AppLogger.d(message = "Inside HomeViewModel")
        getF1HomeDetails()
        getF1News()
    }

    private fun getF1HomeDetails(){
        getF1HomeDetailsUseCase().onEach { resource ->
            when(resource){
                is Resource.Loading -> {
                    AppLogger.d(message = "Loading Home Details")
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
                            f1HomeDetails = resource.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    AppLogger.d(message = "Success Loading Home Details")
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = resource.error,
                            isLoading = false
                        )
                    }
                    AppLogger.e(message = "Error Loading Home Details")
                }
                else -> Unit
            }


        }.launchIn(viewModelScope)
    }


    private fun getF1News(){
        AppLogger.d(message = "GetF1News function run without crashing")

        getF1NewsUseCase().onEach { resource ->
            when(resource){
                is Resource.Loading -> {
                    AppLogger.d(message = "Loading News")
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
                            news = resource.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    AppLogger.d(message = "Success Loading News ")
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = resource.error,
                            isLoading = false
                        )
                    }
                    AppLogger.e(message = "Error Loading News")
                }
                else -> Unit
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.RetryFetch -> {
                getF1HomeDetails()
                getF1News()
            }
        }
    }

}