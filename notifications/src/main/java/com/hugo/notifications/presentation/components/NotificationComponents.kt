package com.hugo.notifications.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.User.NotificationSettings
import com.hugo.design.components.SwitchComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.notifications.domain.NotificationSettingType

enum class NotificationCardType {
    RaceStartsAlerts,
    AllSessionsAlerts,
    General,
    BreakingNews
}

@Composable
fun NotificationCardList(
    settings: NotificationSettings,
    onSettingChanged: (NotificationSettingType, Boolean) -> Unit
){

    val settingItems = mapOf(
        NotificationCardType.RaceStartsAlerts to (settings.raceStarts to NotificationSettingType.RACE_STARTS),
        NotificationCardType.AllSessionsAlerts to (settings.allSessions to NotificationSettingType.ALL_SESSIONS),
        NotificationCardType.BreakingNews to (settings.breakingNews to NotificationSettingType.BREAKING_NEWS),
        NotificationCardType.General to (settings.general to NotificationSettingType.GENERAL)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp)
    ) {
        settingItems.entries.forEachIndexed { index, entry ->
            val (uiType, settingData) = entry
            val (isChecked, settingType) = settingData

            NotificationCardComponent(
                notificationText = uiType.name.replace(Regex("([a-z])([A-Z])"), "$1 $2"),
                isChecked = isChecked,
                onCheckedChange = { newCheckedState ->
                    // When a switch is toggled, call the ViewModel's function
                    onSettingChanged(settingType, newCheckedState)
                }
            )

            if (index < settingItems.size - 1) {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(AppTheme.colorScheme.onSecondary.copy(alpha = 0.1f))
                )
            }
        }
    }
}

@Composable
fun NotificationCardComponent(
    notificationText: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ){
                Text(
                    text = notificationText,
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colorScheme.onSecondary,
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ){
                SwitchComponent(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                )
            }

        }
    }
}

@Preview
@Composable
fun ProfileScreenCardComponentPreview(){
    AppTheme(isDarkTheme = true){
        //ProfileScreenCardList()

        NotificationCardList(
            settings = NotificationSettings(
                raceStarts = true,
                allSessions = false,
                breakingNews = true,
                general = false
            ),
            onSettingChanged = { settingType, isEnabled ->
                // Handle setting change here
            }
        )

    }
}