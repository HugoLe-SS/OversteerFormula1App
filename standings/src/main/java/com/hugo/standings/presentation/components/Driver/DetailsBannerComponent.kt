package com.hugo.standings.presentation.components.Driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.design.components.ButtonComponent
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.R
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo

@Composable
fun StandingsDetailsBannerComponent(
    driverDetails: DriverDetails?= null,
    constructorDetails: ConstructorDetails?= null,
    driverClickInfo: DriverClickInfo? = null,
    constructorClickInfo: ConstructorClickInfo? = null,
    buttonClicked: (String) -> Unit = {},
){
    val teamColor = if (driverDetails != null || driverClickInfo != null) {
        AppColors.Teams.colors[driverClickInfo?.constructorId] ?: Color.Transparent
    } else {
        AppColors.Teams.colors[constructorClickInfo?.constructorId] ?: Color.Transparent
    }

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            teamColor
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(brush = gradientBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (driverDetails != null && driverClickInfo != null) {
                DriverDetailsBanner(
                    driverDetails = driverDetails,
                    driverClickInfo = driverClickInfo,
                    teamColor = teamColor
                )
            } else if (constructorDetails != null && constructorClickInfo != null) {
                ConstructorDetailsBanner(
                    constructorDetails = constructorDetails,
                    constructorClickInfo = constructorClickInfo,
                )
            }
        }

        // View Results Button
        ButtonComponent(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            text = stringResource(R.string.view_results),
            buttonClicked = {
                buttonClicked(
                    driverClickInfo?.driverId ?: constructorClickInfo?.constructorId ?: ""
                )
            }
        )
    }


}


@Composable
fun DriverDetailsBanner(
    driverDetails: DriverDetails,
    driverClickInfo: DriverClickInfo,
    teamColor: Color = AppColors.Teams.colors[driverClickInfo.constructorId] ?: Color.Transparent,
){
    Text(
        text = "${driverClickInfo.givenName} ",
        style = AppTheme.typography.titleLarge,
        color = AppTheme.colorScheme.onSecondary
    )

    Text(
        text = "${driverClickInfo.familyName} ",
        style = AppTheme.typography.titleNormal,
        color = teamColor
    )

//    //Season
    Text(
        text = "${driverClickInfo.season} season",
        style = AppTheme.typography.labelSmall,
        color = AppTheme.colorScheme.onSecondary,
    )

    //Position
    Text(
        text = buildAnnotatedString {
            append("${driverClickInfo.position} ")
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
            append("${driverClickInfo.points} ")
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
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageComponent(
            imageResourceValue = R.drawable.ic_trophy,
            contentDescription = "Trophy icon",
            modifier = Modifier.size(24.dp)
        )
        Column {
            Text(
                if(driverClickInfo.wins <= "1") {
                    "${driverClickInfo.wins} Win"
                } else {
                    "${driverClickInfo.wins} Wins"
                },
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.onSecondary
            )
        }
    }

    //Podiums, Poles, DNF?
}

@Composable
fun ConstructorDetailsBanner(
    constructorDetails: ConstructorDetails,
    constructorClickInfo: ConstructorClickInfo,
    teamColor: Color = AppColors.Teams.colors[constructorClickInfo.constructorId] ?: Color.Transparent,
){
    Text(
        text = "${constructorClickInfo.constructorName} ",
        style = AppTheme.typography.titleLarge,
        color = AppTheme.colorScheme.onSecondary
    )

    Text(
        text = "${constructorDetails.chassis}",
        style = AppTheme.typography.titleNormal,
        color = teamColor
    )

    //Season
    Text(
        text = "${constructorClickInfo.season} season",
        style = AppTheme.typography.labelSmall,
        color = AppTheme.colorScheme.onSecondary,
    )

    //Position
    Text(
        text = buildAnnotatedString {
            append("${constructorClickInfo.position} ")
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
            append("${constructorClickInfo.points} ")
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

    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageComponent(
            imageResourceValue = R.drawable.ic_trophy,
            contentDescription = "Trophy icon",
            modifier = Modifier.size(24.dp)
        )
        Column {
            Text(
                if(constructorClickInfo.wins <= "1") {
                    "${constructorClickInfo.wins} Win"
                } else {
                    "${constructorClickInfo.wins} Wins"
                       },
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.onSecondary
            )
        }
    }
    //Podiums, Poles, DNF?
}
