package com.hugo.schedule.presentation.components.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.SessionInfo
import com.hugo.design.R.drawable
import com.hugo.design.components.ButtonComponent
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.design.utilities.Circuit
import com.hugo.schedule.R
import com.hugo.schedule.presentation.screens.Home.ScheduleType
import com.hugo.utilities.AppUtilities
import com.hugo.utilities.com.hugo.utilities.Navigation.model.CountDownInfo

@Composable
fun ScheduleBannerComponent(
    calendarInfo: F1CalendarInfo?= null,
    countdown: CountDownInfo?= null,
    buttonClicked: (F1CalendarInfo) -> Unit = {},
    currentType: ScheduleType = ScheduleType.UPCOMING,
    imageUrl: Int? = null

    ){
    val circuitColor = AppColors.Circuit.colors[AppColors.Circuit.circuitContinentMap[calendarInfo?.circuitId] ?: ""] ?: AppTheme.colorScheme.onSecondary
    val mainRaceDate = AppUtilities.parseDate(calendarInfo?.mainRaceDate)
    val fp1Date = AppUtilities.parseDate(calendarInfo?.firstPractice?.date?:"")
    
    val circuitImg = Circuit.getCircuitImageRes(calendarInfo?.circuitId ?: "")

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            circuitColor.copy(alpha = 0.6f),
            AppTheme.colorScheme.background,
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        onClick = {
            calendarInfo?.let {
                buttonClicked(it)
            }
        } ,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp)
        ,
    )
    {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(brush = gradientBrush)
            )
            {
                Row(
                    modifier = Modifier
                        .weight(5f)
                        .fillMaxSize()
                        .padding(12.dp)
                ){
                    //Schedule Details
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.Start
                    ){
                        //Round, RaceName
                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxSize()
                        ){

                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Round ${calendarInfo?.round} ",
                                style = AppTheme.typography.labelNormal,
                                color = AppTheme.colorScheme.onSecondary,
                            )


                            Text(
                                modifier = Modifier.weight(3f),
                                text = calendarInfo?.raceName?:"",
                                style = AppTheme.typography.titleLarge,
                                color = AppTheme.colorScheme.onSecondary
                            )
                        }

                        //circuit Name, date
                        Column (
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ){

                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize(),
                                text = calendarInfo?.locality?:"",
                                style = AppTheme.typography.titleNormal,
                                color = circuitColor
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ){
                                Text(
                                    text = "${fp1Date?.day} - ${mainRaceDate?.day} ${mainRaceDate?.monthFull}",
                                    style = AppTheme.typography.labelNormal,
                                    color = AppTheme.colorScheme.onSecondary,
                                )
                            }

                        }

                    }

                    //Circuit Image and CountDown
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        ImageComponent(
                            modifier = Modifier.weight(2f),
                            imageResourceValue = circuitImg ?: imageUrl,
                            contentDescription = "Banner",
                            contentScale = ContentScale.Fit
                        )

                        //Date time countdown
                        if(currentType == ScheduleType.UPCOMING){
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                            )
                            {
                                Text(
                                    modifier = Modifier
                                        .weight(2f)
                                        .align(Alignment.CenterHorizontally),
                                    text = if(countdown?.status!= "Live"){
                                        "${countdown?.sessionName} Starts in"
                                    } else{
                                        "${countdown.sessionName}  is now ${countdown.status}"
                                    },
                                    style = AppTheme.typography.labelNormal,
                                    color = AppTheme.colorScheme.onSecondary,
                                )


                                Row(
                                    modifier = Modifier
                                        .weight(3f)
                                        .fillMaxWidth()
                                ){
                                    if(countdown?.status != "Live")
                                    {
                                        //Days
                                        Column (
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Bottom
                                        ){
                                            countdown?.let {
                                                Text(
                                                    text = it.days,
                                                    style = AppTheme.typography.titleNormal,
                                                    color = AppTheme.colorScheme.onSecondary,
                                                )

                                                Text(
                                                    text = if (it.days <= "1") "Day" else "Days",
                                                    style = AppTheme.typography.labelSmall,
                                                    color = AppTheme.colorScheme.onSecondary,
                                                )
                                            }

                                        }

                                        //Hours
                                        Column (
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Bottom
                                        ){

                                            countdown?.let {
                                                Text(
                                                    text = it.hours,
                                                    style = AppTheme.typography.titleNormal,
                                                    color = AppTheme.colorScheme.onSecondary,
                                                )

                                                Text(
                                                    text = if(it.hours <= "1") "Hour" else "Hours",
                                                    style = AppTheme.typography.labelSmall,
                                                    color = AppTheme.colorScheme.onSecondary,
                                                )
                                            }

                                        }

                                        //Minutes
                                        Column (
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Bottom
                                        ){
                                            countdown?.let {
                                                Text(
                                                    text = it.minutes,
                                                    style = AppTheme.typography.titleNormal,
                                                    color = AppTheme.colorScheme.onSecondary,
                                                )

                                                Text(
                                                    text = if(it.minutes <= "1") "Minute" else "Minutes",
                                                    style = AppTheme.typography.labelSmall,
                                                    color = AppTheme.colorScheme.onSecondary,
                                                )

                                            }

                                        }
                                    }
                                    else {
                                        // Progress Bar
                                        ProgressBarComponent(
                                            countdown = countdown,
                                            color = circuitColor
                                        )
                                    }

                                }

                            }
                        }
                        else{
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                            )
                            {
                                Text(
                                    modifier = Modifier
                                        .weight(2f)
                                        .align(Alignment.CenterHorizontally),
                                    text = "Race Completed",
                                    style = AppTheme.typography.labelNormal,
                                    color = AppTheme.colorScheme.onSecondary,
                                )

                                Row(
                                    modifier = Modifier
                                        .weight(3f)
                                        .fillMaxWidth()
                                ){


                                }

                            }
                        }



                    }
                }

                //Button

                ButtonComponent(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.End)
                        .padding(end = 12.dp),
                    text = stringResource(R.string.view_schedule),
                    buttonClicked = {
                        calendarInfo?.let {
                            buttonClicked(it)
                        }
                    },
                )
            }


    }


}

@Preview
@Composable
fun ScheduleBannerComponentPreview(){
    AppTheme(isDarkTheme = true){
        ScheduleBannerComponent(
            calendarInfo = F1CalendarInfo(
                circuitId = "marina_bay",
                total = 12,
                season = "2025",
                round = "18",
                circuit = "Marina Bay",
                raceName = "Marina Bay Street Circuit",
                locality = "Marina Bay",
                country = "Singapore",
                mainRaceDate = "2025-10-05",
                mainRaceTime = "12:00:00Z",
                firstPractice = SessionInfo(
                    "2025-10-03",
                    "09:30:00Z"
                ),
                secondPractice = SessionInfo(
                    "2025-10-03",
                    "09:30:00Z"
                ),
                thirdPractice = SessionInfo(
                    "2025-10-03",
                    "09:30:00Z"
                ),
                qualifying = SessionInfo(
                    "2025-10-03",
                    "09:30:00Z"
                ),
                sprintQualifying = SessionInfo(
                    "2025-10-03",
                    "09:30:00Z"
                ),
                sprintRace = SessionInfo(
                    "2025-10-03",
                    "09:30:00Z"
                )
            ),
            imageUrl = drawable.circuit_monaco,
            countdown = CountDownInfo(
                sessionName = "FP1",
                days = "1",
                hours = "20",
                minutes = "16",
                status = ""
            )
        )
    }
}

