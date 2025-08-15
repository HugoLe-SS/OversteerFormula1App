package com.hugo.schedule.presentation.components.DetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.AppUtilities


@Composable fun CalendarListItem(
    calendarInfo: F1CalendarInfo,
){
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ){

        val raceDate = AppUtilities.parseDate(calendarInfo.mainRaceDate)
        val fp1Date = AppUtilities.parseDate(calendarInfo.firstPractice?.date?:"")
        val fp2Date = AppUtilities.parseDate(calendarInfo.secondPractice?.date?:"")
        val fp3Date = AppUtilities.parseDate(calendarInfo.thirdPractice?.date?:"")
        val qualiDate = AppUtilities.parseDate(calendarInfo.qualifying?.date?:"")
        val sprintRaceDate = AppUtilities.parseDate(calendarInfo.sprintRace?.date?:"")
        val sprintQualiDate = AppUtilities.parseDate(calendarInfo.sprintQualifying?.date?:"")


        val raceTime = AppUtilities.formatToLocalTime(calendarInfo.mainRaceTime)
        val fp1Time = AppUtilities.formatToLocalTime(calendarInfo.firstPractice?.time?:"")
        val fp2Time = AppUtilities.formatToLocalTime(calendarInfo.secondPractice?.time?:"")
        val fp3Time = AppUtilities.formatToLocalTime(calendarInfo.thirdPractice?.time?:"")
        val qualiTime = AppUtilities.formatToLocalTime(calendarInfo.qualifying?.time?:"")
        val sprintRaceTime = AppUtilities.formatToLocalTime(calendarInfo.sprintRace?.time?:"")
        val sprintQualiTime = AppUtilities.formatToLocalTime(calendarInfo.sprintQualifying?.time?:"")

        //fp1
        CalendarItem(
            day = fp1Date?.day?:"",
            month = fp1Date?.monthShort?:"",
            tag = "Practice 1",
            time = fp1Time
        )

        if(calendarInfo.sprintRace != null){
            //sprint quali
            CalendarItem(
                day = sprintQualiDate?.day?:"",
                month = sprintQualiDate?.monthShort?:"",
                tag = "Sprint Qualifying",
                time = sprintQualiTime
            )
            //sprint race
            CalendarItem(
                day = sprintRaceDate?.day?:"",
                month = sprintRaceDate?.monthShort?:"",
                tag = "Sprint Race",
                time = sprintRaceTime
            )
        }
        else{
            //fp2
            CalendarItem(
                day = fp2Date?.day?:"",
                month = fp2Date?.monthShort?:"",
                tag = "Practice 2",
                time = fp2Time
            )
            //fp3
            CalendarItem(
                day = fp3Date?.day?:"",
                month = fp3Date?.monthShort?:"",
                tag = "Practice 3",
                time = fp3Time
            )
        }


        //quali
        CalendarItem(
            day = qualiDate?.day?:"",
            month = qualiDate?.monthShort?:"",
            tag = "Qualifying",
            time = qualiTime
        )
        //race
        CalendarItem(
            day = raceDate?.day?:"",
            month = raceDate?.monthShort?:"",
            tag = "Race",
            time = raceTime
        )

    }
}

@Composable
fun CalendarItem(
    day: String,
    month: String,
    tag: String,
    time: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //  day/month
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = day,
                style = AppTheme.typography.titleNormal,
                color = AppTheme.colorScheme.onSecondary,
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = month,
                style = AppTheme.typography.labelNormal,
                color = AppTheme.colorScheme.onSecondary,
            )
        }

        // time
        Column(
            modifier = Modifier.weight(6f)
        ) {
            Text(
                text = tag,
                style = AppTheme.typography.titleNormal,
                color = AppTheme.colorScheme.onSecondary,
            )
            Text(
                text = time,
                style = AppTheme.typography.labelNormal,
                color = AppTheme.colorScheme.onSecondary,
            )
        }

    }

    Spacer(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(AppTheme.colorScheme.onBackground)
    )

}

@Preview
@Composable
fun CalendarItemPreview() {
    CalendarItem(
        day = "01",
        month = "Jan",
        tag = "Practice 1",
        time = "12:00"
    )
}

