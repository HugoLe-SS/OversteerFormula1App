package com.hugo.schedule.presentation.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.schedule.domain.usecase.GetF1CalendarUseCase
import com.hugo.utilities.AppUtilities
import com.hugo.utilities.AppUtilities.getNextUpcomingSession
import com.hugo.utilities.Resource
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ScheduleHomeViewModel @Inject constructor(
    private val getF1CalendarUseCase: GetF1CalendarUseCase
) :ViewModel() {

    private val _state = MutableStateFlow(ScheduleHomeUiState())
    val state: StateFlow<ScheduleHomeUiState> = _state

    private val _countdown = MutableStateFlow<AppUtilities.CountDownInfo?>(null)
    val countdown: StateFlow<AppUtilities.CountDownInfo?> = _countdown

    private val _nextSession = MutableStateFlow<AppUtilities.Session?>(null)
    //val nextSession: StateFlow<AppUtilities.Session?> = _nextSession

    val filteredEvents = _state
        .map { state ->
            val currentTime = System.currentTimeMillis()
            state.f1Calendar
                .filter { event ->
                    val eventDateTime = AppUtilities.convertToMillis(date = event.mainRaceDate, time = event.mainRaceTime)
                    when (state.currentType) {
                        ScheduleType.UPCOMING -> eventDateTime > currentTime
                        ScheduleType.PAST -> eventDateTime <= currentTime
                    }
                }.let { filteredList ->
                    if (state.currentType == ScheduleType.PAST) {
                        filteredList.sortedByDescending { event ->
                            AppUtilities.convertToMillis(date = event.mainRaceDate, time = event.mainRaceTime)
                        }
                    } else {
                        filteredList
                    }

                }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        getF1Calendar(season = "current")
    }

    private fun startCountdown(session: AppUtilities.Session?) {
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
                    val calendar = _state.value.f1Calendar
                    val currentEvent = calendar.firstOrNull { it.mainRaceDate == session.date }
                    currentEvent?.let {
                        updateCountdownFromSessions(it)
                    }
                    break
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

//    private fun startCountdown(session: AppUtilities.Session?) {
//        viewModelScope.launch {
//            if (session == null) return@launch
//
//            val sessionStartMillis = AppUtilities.convertToMillis(session.date?:"", session.time?:"")
//            val sessionEndMillis = sessionStartMillis + (session.sessionDuration.times(60 * 1000))
//
//
//            while (true) {
//                val now = System.currentTimeMillis()
//
//                // Stop countdown when session ends
//                if (now >= sessionEndMillis) {
//                    val calendar = _state.value.f1Calendar
//                    val currentEvent = calendar.firstOrNull { it.mainRaceDate == session.date } // crude match
//                    currentEvent?.let {
//                        updateCountdownFromSessions(it)
//                    }
//                    break
//                }
//
//                _countdown.value = AppUtilities.formatToDaysHoursMinutes(session.name,session.date, session.time, session.sessionDuration)
//
//                // restart countdown every minute
//                delay(60_000L)
//            }
//        }
//    }

    fun updateCountdownFromSessions(info: F1CalendarInfo) {
        val sessions = listOf(
            AppUtilities.Session(name = "FP1", date = info.firstPractice?.date, time = info.firstPractice?.time, sessionDuration = 60),
            AppUtilities.Session(name = "FP2", date = info.secondPractice?.date, time = info.secondPractice?.time, sessionDuration = 60 ),
            AppUtilities.Session(name = "FP3", date = info.thirdPractice?.date, time = info.thirdPractice?.time, sessionDuration = 60),
            AppUtilities.Session(name = "Qualifying", date = info.qualifying?.date, time = info.qualifying?.time, sessionDuration = 45),
            AppUtilities.Session(name = "Main Race", date = info.mainRaceDate, time = info.mainRaceTime, sessionDuration = 120),
            AppUtilities.Session(name = "Sprint Qualifying", date = info.sprintQualifying?.date, time = info.sprintQualifying?.time, sessionDuration = 30),
            AppUtilities.Session(name = "Sprint Race", date = info.sprintRace?.date, time = info.sprintRace?.time, sessionDuration = 30),
        )

        val next = getNextUpcomingSession(sessions)
        _nextSession.value = next

        startCountdown(next)
    }


    private fun getF1Calendar(season: String) {
        getF1CalendarUseCase(season).onEach { result ->
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
                            f1Calendar = result.data ?: emptyList()
                        )
                    }
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


    fun onEvent(event: ToggleScheduleEvent) {
        when (event) {
            is ToggleScheduleEvent.SetScheduleType -> {
                _state.update { it.copy(currentType = event.type) }
                AppLogger.d(message = "${state.value.currentType}")
            }
            is ToggleScheduleEvent.ToggleSchedule -> {
                getF1Calendar(season = "current")
            }

        }
    }




}