package com.hugo.result.presentation.screens

sealed class ResultEvent {
    data class RetryFetch(
        val season: String,
        val driverId: String?= null,
        val constructorId: String?= null,
        val circuitId: String? = null,
    ): ResultEvent()
}