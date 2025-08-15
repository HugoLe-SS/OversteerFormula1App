package com.hugo.notifications.domain

interface NotificationRepository {
    suspend fun syncFcmToken(): Result<Unit>
    suspend fun updateNotificationSetting(setting: NotificationSettingType, isEnabled: Boolean): Result<Unit>
}

enum class NotificationSettingType {
    RACE_STARTS, ALL_SESSIONS, BREAKING_NEWS, GENERAL
}

