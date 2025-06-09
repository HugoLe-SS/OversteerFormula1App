package com.hugo.oversteerf1.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.F1HomeDetails
import com.hugo.design.R
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.design.utilities.Circuit
import com.hugo.schedule.presentation.components.HomeScreen.ProgressBarComponent
import com.hugo.utilities.AppUtilities
import com.hugo.utilities.com.hugo.utilities.Navigation.model.CountDownInfo


@Composable
fun UpcomingRaceCardItem(
    cardOnClicked: () -> Unit = {},
    f1HomeDetails: F1HomeDetails,
    countdown: CountDownInfo?

){
    val circuitColor = AppColors.Circuit.colors[AppColors.Circuit.circuitContinentMap[f1HomeDetails.circuitId] ?: ""]
        ?: AppTheme.colorScheme.onSecondary
    val circuitImg = Circuit.getCircuitImageRes(f1HomeDetails.circuitId ?: "")


    val mainRaceDate = AppUtilities.parseDate(f1HomeDetails.raceDate)
    val mainRaceTime = AppUtilities.formatToLocalTime(f1HomeDetails.raceTime)


    RaceScheduleCardComponent(
        cardOnClicked = cardOnClicked,
        backgroundColor = circuitColor.copy(alpha = 0.2f),
        raceName = f1HomeDetails.raceName ?: "",
        raceDate = "${mainRaceDate?.day} ${mainRaceDate?.monthShort}",
        raceTime = mainRaceTime,
        img = circuitImg ?: R.drawable.circuit_imola,
        circuitColor = circuitColor,
        countdown = countdown
    )
}


@Composable
fun RaceScheduleCardComponent(
    countdown: CountDownInfo?= null,
    cardOnClicked: () -> Unit = {},
    backgroundColor : Color,
    circuitColor: Color,
    raceName: String,
    raceDate: String,
    raceTime: String,
    img: Int,
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(100.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = AppTheme.colorScheme.secondary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { cardOnClicked() }

    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            ){
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //Circuit Name,
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = raceName,
                            style = AppTheme.typography.titleNormal,
                            color = AppTheme.colorScheme.onSecondary
                        )
                    }

                    //Race Date, Time
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = raceDate,
                            style = AppTheme.typography.labelSmall,
                            color = AppTheme.colorScheme.onSecondary,
                        )

                        Text(
                            modifier = Modifier.weight(1f),
                            text = raceTime,
                            style = AppTheme.typography.labelSmall,
                            color = AppTheme.colorScheme.onSecondary,
                        )
                    }

                    //Upcoming / Race Results
                    Row(
                        modifier = Modifier
                            .weight(1f)
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
                                        style = AppTheme.typography.labelMini,
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
                                        style = AppTheme.typography.labelMini,
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
                                        style = AppTheme.typography.labelMini,
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


            //Circuit IMG
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                ImageComponent(
//                modifier = Modifier
//                    .size(24.dp),
                    imageResourceValue = img,
                    contentDescription = "Home Details Image",
                )

            }
        }

    }
}


@Preview
@Composable
fun HomeCardComponentPreview(){
    AppTheme(
        isDarkTheme = true
    ){
        val circuitColor = AppColors.Circuit.colors[AppColors.Circuit.circuitContinentMap["monaco"] ?: ""]
            ?: AppTheme.colorScheme.onSecondary

        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(
                AppTheme.colorScheme.background,
                circuitColor.copy(0.6f),
                AppTheme.colorScheme.background,
            )
        )
//        HomeCardComponent(
//            //cardOnClicked = TODO(),
//            backgroundColor = gradientBrush,
//            raceName = "Monaco",
//            raceDate = "May 16",
//            raceTime = "10:00",
//            img = R.drawable.circuit_monaco
//        )

        UpcomingRaceCardItem(
            //cardOnClicked = TODO(),
            f1HomeDetails = F1HomeDetails(
                circuitId = "monaco",
                raceName = "Monaco",
                raceDate = "2025-05-25",
                raceTime = "13:00:00Z",
//                topDriversStandings = TODO(),
//                topConstructorsStandings = TODO(),
//                lastRaceName = TODO(),
//                lastRacePodiums = TODO()
            ),
            countdown = CountDownInfo(
                sessionName = "Main Race",
                days = "7",
                hours = "9",
                minutes = "35",
                status = "",
                progress = 1f,

            )
        )
    }
}
