package com.hugo.schedule.presentation.components.DetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.design.R.drawable
import com.hugo.design.components.ScheduleDetailsBannerComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.design.utilities.Circuit
import com.hugo.utilities.AppUtilities
import java.time.Duration
import java.time.ZoneOffset
import java.time.ZonedDateTime


@Composable
fun F1CalendarBannerListItem(
    circuitDetails: F1CircuitDetails?= null,
    calendarInfo: F1CalendarInfo?= null,
    viewResultButtonClicked:(F1CircuitDetails) -> Unit = {},
){
    val circuitColor = AppColors.Circuit.colors[AppColors.Circuit.circuitContinentMap[calendarInfo?.circuitId] ?: ""] ?: AppTheme.colorScheme.onSecondary
    val circuitImg = Circuit.getCircuitImageRes(calendarInfo?.circuitId ?: "")
    val mainRaceDate = AppUtilities.parseDate(calendarInfo?.mainRaceDate)

    //Compare Time to return if the results button should be shown
    val mainRaceZonedTime = AppUtilities.parseUtcRaceDateTimeToZoned(
        mainRaceDate = calendarInfo?.mainRaceDate,
        mainRaceTime = calendarInfo?.mainRaceTime
    )
    val showResultsButton = mainRaceZonedTime?.let { Duration.between(it, ZonedDateTime.now(
        ZoneOffset.UTC)).toHours() >= 12 } ?: false

    val fp1Date = AppUtilities.parseDate(calendarInfo?.firstPractice?.date?:"")

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            circuitColor.copy(alpha = 0.6f),
            AppTheme.colorScheme.background,
        )
    )

    Box(
        modifier = Modifier
            .background(brush = gradientBrush)
    ) {
        if(calendarInfo != null && circuitDetails != null) {
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
                circuitImgUrl = circuitImg ?: drawable.circuit_americas,
                buttonClicked = {viewResultButtonClicked(
                    F1CircuitDetails(
                        circuitId = circuitDetails.circuitId,
                        imageUrl = circuitDetails.imageUrl,
                        circuitDescription = circuitDetails.circuitDescription,
                        circuitFacts = circuitDetails.circuitFacts,
                        circuitBasicInfo = circuitDetails.circuitBasicInfo,
                        circuitPodiums = circuitDetails.circuitPodiums,
                        fastestLaps = circuitDetails.fastestLaps,
                        fastestPit = circuitDetails.fastestPit,
                        dotd = circuitDetails.dotd
                    )
                )
                },
                isPastRace = showResultsButton
            )
        }
    }

}