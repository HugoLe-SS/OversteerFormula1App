package com.hugo.standings.presentation.components.Driver

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.R
import com.hugo.utilities.AppUtilities

@Composable
fun StandingsDetailsBannerComponent(
    driver: DriverRaceResultsInfo,
    teamColor: Color = AppColors.Teams.colors[driver.constructorId.lowercase()] ?: Color.Transparent,
    gradientBrush: Brush = Brush.horizontalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            teamColor
        )
    ),
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(brush = gradientBrush)

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
                color = teamColor
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
                            color = teamColor
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
                            color = teamColor
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
fun StandingsDetailsListItem(
    driverRace: DriverRaceResultsInfo,
    driverQuali: DriverQualifyingResultsInfo,
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),

        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.background
        )
    ){
        DriverRaceResultListItem(
            driverRace = driverRace, driverQuali = driverQuali
        )

        Spacer(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(AppTheme.colorScheme.onBackground)
        )
    }


}

@Composable fun DriverRaceResultListItem(
    driverRace: DriverRaceResultsInfo,
    driverQuali: DriverQualifyingResultsInfo,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        //row 1
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
}

@Composable fun DriverBioList(
    driverDetails: DriverDetails,
    driverRace: DriverRaceResultsInfo,
){
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ){
        StandingsBioItem(
            imageResourceValue = R.drawable.ic_trophy,
            info = driverRace.driverCode,
            infoTag = stringResource(R.string.driver_code)
        )

        StandingsBioItem(
            imageResourceValue = R.drawable.ic_mclaren,
            info = driverRace.constructorName,
            infoTag = stringResource(R.string.team)
        )

        StandingsBioItem(
            imageResourceValue = R.drawable.ic_arrow_up,
            info = "${driverDetails.firstEntry}",
            infoTag = stringResource(R.string.first_entry)
        )

        driverDetails.firstWin?.let {
            StandingsBioItem(
                imageUrl = driverDetails.imageUrl,
                info = "${driverDetails.firstWin}",
                infoTag = stringResource(R.string.first_win)
            )
        } ?: StandingsBioItem(
            imageResourceValue = R.drawable.ic_trophy,
            info = "${driverDetails.firstPodium}",
            infoTag = stringResource(R.string.first_podium)
        )


        StandingsBioItem(
            imageResourceValue = R.drawable.ic_equal,
            info = "${driverDetails.wdc}",
            infoTag = stringResource(R.string.world_championships)
        )

        StandingsBioItem(
            imageResourceValue = R.drawable.ic_equal,
            info = driverRace.dateOfBirth,
            infoTag = stringResource(R.string.date_of_birth)
        )

    }
}

@Composable fun ConstructorBioList(
    constructorDetails: ConstructorDetails,
){

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ){
        StandingsBioItem(
            imageResourceValue = R.drawable.ic_mclaren,
            info = constructorDetails.firstDriver?.getOrNull(1) ?: "",
            infoTag = constructorDetails.firstDriver?.getOrNull(2) ?: "",
        )

        StandingsBioItem(
            imageResourceValue = R.drawable.ic_mclaren,
            info = constructorDetails.secondDriver?.getOrNull(1) ?: "",
            infoTag = constructorDetails.secondDriver?.getOrNull(2) ?: "",
        )

        StandingsBioItem(
            imageResourceValue = R.drawable.ic_mclaren,
            info = constructorDetails.chassis?: "",
            infoTag = stringResource(R.string.chassis)
        )

        StandingsBioItem(
            imageResourceValue = R.drawable.ic_arrow_up,
            info = constructorDetails.powerUnit?: "",
            infoTag = stringResource(R.string.power_unit)
        )

        StandingsBioItem(
            imageResourceValue = com.hugo.design.R.drawable.ic_person,
            info = "${constructorDetails.teamPrincipal}",
            infoTag = stringResource(R.string.team_principal)
        )

        StandingsBioItem(
            imageResourceValue = com.hugo.design.R.drawable.ic_trophy,
            info = "${constructorDetails.firstEntry}",
            infoTag = stringResource(R.string.first_entry)
        )

        StandingsBioItem(
            imageResourceValue = com.hugo.design.R.drawable.ic_trophy,
            info = "${constructorDetails.wcc}",
            infoTag = "Constructors Championship"
        )

        StandingsBioItem(
            imageResourceValue = com.hugo.design.R.drawable.ic_trophy,
            info = "${constructorDetails.wdc}",
            infoTag = "Drivers Championship"
        )

    }
}

@Composable
fun StandingsBioItem(
    imageResourceValue: Int?= null,
    imageUrl: String? = null,
    info: String,
    infoTag: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Img/Icon
        ImageComponent(
            modifier = Modifier
                .weight(1f)
                .size(18.dp),
            imageResourceValue = imageResourceValue,
            imageUrl = imageUrl,
            colorFilter = ColorFilter.tint(color = AppTheme.colorScheme.onSecondary),
        )

        Column (
            modifier = Modifier.weight(6f)
        ){
            Text(
                text = info,
                style = AppTheme.typography.titleNormal,
                color = AppTheme.colorScheme.onSecondary,
            )
            Text(
                text = infoTag,
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