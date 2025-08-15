package com.hugo.schedule.presentation.components.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.design.components.CardComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.design.utilities.Circuit
import com.hugo.utilities.AppUtilities
import com.hugo.utilities.AppUtilities.toShortGPFormat

@Composable
fun F1CalendarListItem(
    calendarInfo: F1CalendarInfo,
    cardClicked: (F1CalendarInfo) -> Unit = {}
){
    val raceDate = AppUtilities.parseDate(calendarInfo.mainRaceDate)
    val teamColor = AppColors.Circuit.colors[AppColors.Circuit.circuitContinentMap[calendarInfo.circuitId] ?: ""] ?: AppTheme.colorScheme.onSecondary

    val circuitImg = remember(calendarInfo.circuitId) {
        Circuit.getCircuitImageRes(calendarInfo.circuitId ?: "")
    }
    val raceName = calendarInfo.raceName.toShortGPFormat()


    CardComponent(
        cardOnClicked ={
            cardClicked(
                calendarInfo // pass the whole thing in the F1CalendarInfo object
            )
        },
        firstColumnDescription = "${raceDate?.day} ",
        firstColumnDetails = "${raceDate?.monthShort} ",
        secondColumnDescription = "$raceName ",
        secondColumnDetails = "${calendarInfo.locality} ",
        circuitImage = circuitImg,
        teamColor = teamColor,
    )

    Spacer(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(AppTheme.colorScheme.onBackground)
    )

}


