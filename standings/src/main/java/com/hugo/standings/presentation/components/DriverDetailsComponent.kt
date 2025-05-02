package com.hugo.standings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.R
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.utilities.AppUtilities

@Composable
fun DriverDetailsBannerComponent(
    driver: DriverRaceResultsInfo,
    gradientBrush: Brush = Brush.horizontalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            AppColors.Teams.mclaren
        )
    ),
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(brush = gradientBrush),
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.SpaceAround
        ){
            Text(
                text = "${driver.givenName}",
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.onSecondary
            )

            Text(
                text = "${driver.familyName}",
                style = AppTheme.typography.titleNormal,
                color = AppColors.Teams.mclaren,
            )

            //Season
            Text(
                text = "${driver.season} season",
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.onSecondary,
            )

            //Position
            Text(
                text = buildAnnotatedString {
                    append("${driver.position} ")
                    withStyle(
                        style = AppTheme.typography.labelNormal.toSpanStyle().copy(
                            color = AppColors.Teams.mclaren
                        )
                    ) {
                        append("POS")
                    }
                },
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.onSecondary
            )

            //Points
            Text(
                text = buildAnnotatedString {
                    append("${driver.points} ")
                    withStyle(
                        style = AppTheme.typography.labelNormal.toSpanStyle().copy(
                            color = AppColors.Teams.mclaren
                        )
                    ) {
                        append("POINTS")
                    }
                },
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.onSecondary
            )
            //Wins get through ROOM DB
//            Row(
//
//            )
//            {
//                ImageComponent(
//                    imageResourceValue = R.drawable.ic_trophy,
//                    contentDescription = "Trophy icon"
//                )
//                Column {
//                    Text(
//                        text = "${driver.wins}",
//                        style = AppTheme.typography.titleNormal
//                    )
//                    Text(
//                        if(driver.wins <= "1") {
//                            "${driver.wins} Win"
//                        } else {
//                            "${driver.wins} Wins"
//                        },
//                        style = AppTheme.typography.titleNormal
//                    )
//                }
//            }

            //Podiums, Poles, DNF?
        }
    }

}

@Composable
fun DriverDetailsListItem(
    driverRace: DriverRaceResultsInfo,
    driverQuali: DriverQualifyingResultsInfo,
    driverCardClicked: (String) -> Unit = {}
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .wrapContentHeight()
            .clickable {
                driverCardClicked(driverRace.driverId)
            }
            .padding(12.dp),

        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.background
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.padding(12.dp),
        ) {
            //row 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    //.padding(bottom = 4.dp),
            ){
                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(2f),
                    //textAlign = TextAlign.Center,
                    text = "${driverRace.position} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(8f),
                    //textAlign = TextAlign.Center,
                    text = "${driverRace.circuitName} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(4f),
                    textAlign = TextAlign.Center,
                    text = "${driverRace.points} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

            }

            //row 2
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Row(
                    modifier = Modifier.weight(2f)
                ){
                    DriverPositionChange(
                        grid = driverRace.grid,
                        position = driverRace.position
                    )
                }

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(8f),
                    //textAlign = TextAlign.Center,
                    text = "${driverRace.country} ",
                    style = AppTheme.typography.labelSmall,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(4f),
                    textAlign = TextAlign.Center,
                    text = "PTS ",
                    style = AppTheme.typography.labelSmall,
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
        ImageComponent(
            modifier = Modifier.size(18.dp),
            imageResourceValue = R.drawable.ic_arrow_up,
            contentDescription = "Arrow up icon",
            colorFilter = ColorFilter.tint(Color.Green)
        )
        Text(
            text = positionChange,
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colorScheme.onSecondary,
        )
    } else if(grid.toInt() < position.toInt())
    {
        ImageComponent(
            modifier = Modifier.size(18.dp),
            imageResourceValue = R.drawable.ic_arrow_down,
            contentDescription = "Arrow down icon",
            colorFilter = ColorFilter.tint(Color.Red)
        )
        Text(
            text = positionChange,
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colorScheme.onSecondary,
        )
    }
    else{
        ImageComponent(
            modifier = Modifier.size(18.dp),
            imageResourceValue = R.drawable.ic_equal,
            contentDescription = "Arrow ~~~~",
            colorFilter = ColorFilter.tint(Color.Green)
        )
        Text(
            text = positionChange,
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colorScheme.onSecondary,
        )
    }
}