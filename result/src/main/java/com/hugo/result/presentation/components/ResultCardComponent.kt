package com.hugo.result.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.result.R
import com.hugo.result.presentation.screens.DriverStatus
import com.hugo.utilities.AppUtilities


@Composable
fun ResultCardComponent(
    modifier: Modifier = Modifier,
    elevation: CardElevation = CardDefaults.cardElevation(2.dp),
    color: CardColors = CardDefaults.cardColors(
        containerColor = AppTheme.colorScheme.background
    ),
    position: Int,
    grid: String,
    driverName: String?= null,
    raceName: String?= null,
    constructorName: String?= null,
    circuitNation: String?= null,
    points: String,
    teamColor: Color,
    interval: String?= null,
    gapToLeader: String?= null,
    fastestLap: String?= null,
    status: String?= null
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(12.dp),
        elevation = elevation,
        colors = color
    ) {
        ResultCardDetails(
            position = position,
            grid = grid,
            driverName = driverName,
            constructorName = constructorName,
            raceName = raceName,
            circuitNation = circuitNation,
            points = points,
            teamColor = teamColor,
            interval = interval,
            gapToLeader = gapToLeader,
            fastestLap = fastestLap,
            status = status
        )
    }
}

@Composable
fun ResultCardDetails(
    position: Int,
    grid: String? = "",
    driverName: String? = "",
    raceName: String? = "",
    constructorName: String? = "",
    circuitNation: String?= "",
    points: String,
    teamColor: Color,
    interval: String?= null,
    gapToLeader: String?= null,
    fastestLap: String?= null,
    status: String?= null
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        //Row1: position, position gained , driverName, constructorName, points
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
        ) {
            //Column 1
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = position.toString() ?: "",
                        style = AppTheme.typography.labelNormal,
                        color = AppTheme.colorScheme.onSecondary,
                    )
                }

                Row(
                    modifier = Modifier.weight(2f)
                ) {
                    DriverPositionChange(
                        grid = grid ?: "",
                        position = position.toString()
                    )
                }
            }

            //Column 2
            Column(
                modifier = Modifier
                    .weight(6f)
                    .fillMaxSize()
                    .padding(start = 8.dp),
            ) {

                //For Driver Result
                if(raceName!= null && circuitNation != null)
                {
                    IdentityColumn(modifier = Modifier.weight(1f),
                        description = raceName,
                        details = circuitNation,
                        teamColor = teamColor
                    )
                }

                //For Constructor Result
                if(driverName!= null && raceName != null)
                {
                    IdentityColumn(modifier = Modifier.weight(1f),
                        description = driverName,
                        details = raceName,
                        teamColor = teamColor
                    )
                }

                //For Circuit Result
                if(driverName!= null && constructorName != null) {
                    IdentityColumn(
                        modifier = Modifier.weight(1f),
                        description = driverName,
                        details = constructorName,
                        teamColor = teamColor
                    )
                }
            }

            //Column 3
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DriverStatusComponent(
                    driverStatus = DriverStatus.fromString(status),
                    points = points,
                    modifier = Modifier.weight(1f)
                )

            }


        }

        //Time, FastestLap, Gap to Leader
        Box(
            modifier = Modifier
                .weight(3f)
                .padding(top = 8.dp)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
            ) {

                if(status != "Lapped")
                {
                    // Time for Leader / Interval for the rest of the field
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ){
                        RaceTimeComponent(
                            time = interval,
                            description = stringResource(R.string.interval),
                            icon = R.drawable.ic_interval)
                    }

                    //Gap to Leader / for the leader is 0
                    Box (
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ){
                        RaceTimeComponent(
                            time = gapToLeader,
                            description = if(position == 1) stringResource(R.string.time) else stringResource(R.string.gap_to_leader),
                            icon = R.drawable.ic_gap)
                    }
                } else{

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ){
                        RaceTimeComponent(
                            time = "_",
                            description = stringResource(R.string.interval),
                            icon = R.drawable.ic_interval)
                    }

                    //Gap to Leader for Lapped Car will be determined by the interval formula
                    Box (
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ){
                        RaceTimeComponent(
                            time = interval,
                            description = stringResource(R.string.gap_to_leader),
                            icon = R.drawable.ic_gap)
                    }
                }



                // Fastest lap set by each driver in the race
                Box (
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ){
                    RaceTimeComponent(time = fastestLap, description = "Fastest lap", icon = R.drawable.ic_timer)
                }


            }


        }

    }

}

@Composable
fun IdentityColumn(
    modifier: Modifier = Modifier,
    description: String,
    details: String,
    teamColor: Color
){
    Box(
        modifier = modifier
    ){
        Text(
            text = description,
            style = AppTheme.typography.titleNormal,
            color = AppTheme.colorScheme.onSecondary,
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ){
        Text(
            text = details,
            style = AppTheme.typography.labelSmall,
            color = teamColor,
        )
    }
}

@Composable
fun RaceTimeComponent(
    time: String?= null,
    description: String,
    icon: Int
){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp)
    ){
        //Icon
        Column (
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            ImageComponent(
                modifier = Modifier
                    .weight(1f),
                imageResourceValue = icon,
                contentDescription = "Icon for RaceTimeComponent",
            )

            Box(modifier = Modifier.weight(1f)
            ){}
        }

        //Time
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ){
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ){

                Text(
                    text = if(time.isNullOrEmpty()) "_" else time,
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colorScheme.onSecondary
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ){
                Text(
                    text = description,
                    style = AppTheme.typography.labelMini,
                    color = AppTheme.colorScheme.secondary
                )
            }

        }
    }
}

@Composable
fun DriverPositionChange(
    //driver: DriverRaceResultsInfo
    grid: String,
    position: String,
){
    val positionChange = AppUtilities.calculatePositionChange(grid, position)

    //Tang Hang
    if(grid.toInt() > position.toInt()){
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ){
            ImageComponent(
                modifier = Modifier.size(14.dp),
                imageResourceValue = R.drawable.ic_arrow_up,
                contentDescription = stringResource(R.string.arrow_up_icon),
                colorFilter = ColorFilter.tint(Color.Green)
            )
            Text(
                text = positionChange,
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.onSecondary,
            )
        }

    } else if(grid.toInt() < position.toInt())
    {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ){
            ImageComponent(
                modifier = Modifier
                    .size(14.dp),
                imageResourceValue = R.drawable.ic_arrow_down,
                contentDescription = stringResource(R.string.arrow_down_icon),
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Text(
                text = positionChange,
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.onSecondary,
            )
        }

    }
    else{
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ){
            ImageComponent(
                modifier = Modifier.size(14.dp),
                imageResourceValue = R.drawable.ic_equal,
                contentDescription = stringResource(R.string.arrow),
                colorFilter = ColorFilter.tint(Color.Green)
            )
            Text(
                text = positionChange,
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.onSecondary,
            )
        }

    }
}


@Composable
fun DriverStatusComponent(
    driverStatus: DriverStatus,
    points: String,
    modifier: Modifier = Modifier
) {
    // Determine the primary text to display based on the status
    val primaryTextToDisplay = when (driverStatus) {
        DriverStatus.Finished -> points ?: ""
        DriverStatus.Lapped -> points ?: ""
        DriverStatus.Retired -> "DNF"
        DriverStatus.Disqualified -> "DSQ"
        DriverStatus.DidNotStart -> "DNS"
        DriverStatus.DidNotQualify -> "DNQ"
        DriverStatus.Unknown -> "N/A"
    }

    val primaryTextColor = when (driverStatus) {
        DriverStatus.Retired, DriverStatus.Disqualified, DriverStatus.DidNotStart, DriverStatus.DidNotQualify, -> Color.Red
        else -> AppTheme.colorScheme.onSecondary
    }
    if (driverStatus == DriverStatus.Finished || driverStatus == DriverStatus.Lapped) {
        Box(
            modifier = modifier
        ){
            Text(
                textAlign = TextAlign.Center,
                text = primaryTextToDisplay ?: "",
                style = AppTheme.typography.titleNormal,
                color = primaryTextColor,
            )
        }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.BottomStart
        ){
            Text(
                textAlign = TextAlign.Center,
                text = "PTS",
                style = AppTheme.typography.titleNormal,
                color = AppTheme.colorScheme.onSecondary,
            )
        }
    }
    else {
        Box(
            modifier = modifier
        ){
            Text(
                textAlign = TextAlign.Center,
                text = primaryTextToDisplay ?: "",
                style = AppTheme.typography.titleNormal,
                color = primaryTextColor,
            )
        }

        Box(
            modifier = modifier,
        ){}
    }

}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultCardComponentPreview() {
    AppTheme(isDarkTheme = true) {
        ResultCardComponent(
            position = 1,
            grid = "4",
            driverName = "Lando Norris",
            constructorName = "McLaren",
            points = "18",
            teamColor = Color.Cyan,
            interval = "5.2",
            gapToLeader = "4.1",
            fastestLap = "1:17.988",
            status = "Finished"
        )
    }
}

