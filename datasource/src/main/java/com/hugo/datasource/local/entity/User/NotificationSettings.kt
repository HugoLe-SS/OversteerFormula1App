package com.hugo.datasource.local.entity.User

data class NotificationSettings(
    val raceStarts: Boolean,
    val allSessions: Boolean,
    val breakingNews: Boolean,
    val general: Boolean
)