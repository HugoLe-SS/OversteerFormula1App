package com.hugo.schedule.presentation.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.schedule.domain.usecase.GetF1CalendarUseCase
import com.hugo.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScheduleHomeViewModel @Inject constructor(
    private val getF1CalendarUseCase: GetF1CalendarUseCase
) :ViewModel() {

    private val _state = MutableStateFlow(ScheduleHomeUiState())
    val state: StateFlow<ScheduleHomeUiState> = _state

    init {
        getF1Calendar(season = "current")
    }

    fun getF1Calendar(season: String) {
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
}