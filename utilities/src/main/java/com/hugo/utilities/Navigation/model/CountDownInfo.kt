package com.hugo.utilities.com.hugo.utilities.Navigation.model

data class CountDownInfo(
    val sessionName: String,
    val days: String,
    val hours: String,
    val minutes: String,
    val status: String?,
    val progress: Float = 0f
)