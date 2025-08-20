package com.hugo.oversteerf1.presentation.screens.home

sealed class HomeEvent {
    data object RetryFetch: HomeEvent()

}