package com.hugo.utilities.com.hugo.utilities.Navigation.model

import kotlinx.serialization.Serializable

@Serializable
data class ConstructorClickInfo(
    val constructorId: String,
    val constructorName: String,
    val season: String,
    val nationality: String,
    val position: String,
    val points: String,
    val wins: String,
)