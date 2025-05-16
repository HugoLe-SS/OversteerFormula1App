package com.hugo.schedule.presentation.components.DetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.design.components.ScheduleDetailsBannerComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.AppUtilities


@Composable
fun F1CalendarBannerListItem(
    circuitDetails: F1CircuitDetails,
    calendarInfo: F1CalendarInfo,
){
    val circuitColor = AppColors.Circuit.colors[AppColors.Circuit.circuitContinentMap[calendarInfo.circuitId] ?: ""] ?: AppTheme.colorScheme.onSecondary
    val mainRaceDate = AppUtilities.parseDate(calendarInfo.mainRaceDate)
    val fp1Date = AppUtilities.parseDate(calendarInfo.firstPractice?.date?:"")

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            circuitColor.copy(alpha = 0.6f),
            circuitColor.copy(alpha = 0.6f),
            AppTheme.colorScheme.background,
        )
    )

    Box(
        modifier = Modifier
            .background(brush = gradientBrush)
    ) {
            ScheduleDetailsBannerComponent(
                round = calendarInfo.round,
                raceName = calendarInfo.raceName ?: "",
                circuitName = calendarInfo.circuit,
                date = "${fp1Date?.day} - ${mainRaceDate?.day} ${mainRaceDate?.monthFull}",
                circuitLength = circuitDetails.circuitBasicInfo?.getOrNull(0) ?: "",
                laps = circuitDetails.circuitBasicInfo?.getOrNull(1) ?: "",
                turns = circuitDetails.circuitBasicInfo?.getOrNull(2) ?: "",
                topSpeed = circuitDetails.circuitBasicInfo?.getOrNull(3) ?: "",
                elevation = circuitDetails.circuitBasicInfo?.getOrNull(4) ?: "",
                circuitColor = circuitColor,
                circuitImgUrl = circuitDetails.imageUrl ?: "",
            )
        }

}