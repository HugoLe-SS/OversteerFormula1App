package com.hugo.standings.presentation.components.StandingsDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.design.components.ButtonComponent
import com.hugo.design.components.StandingsDetailsBannerComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.R
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo

@Composable
fun StandingsBannerListItem(
    driverDetails: DriverDetails?= null,
    constructorDetails: ConstructorDetails?= null,
    driverClickInfo: DriverClickInfo? = null,
    constructorClickInfo: ConstructorClickInfo? = null,
    buttonClicked: (String) -> Unit = {},
){
    val teamColor = if (driverDetails != null || driverClickInfo != null) {
        AppColors.Teams.colors[driverClickInfo?.constructorId] ?: AppTheme.colorScheme.onSecondary
    } else {
        AppColors.Teams.colors[constructorClickInfo?.constructorId] ?: AppTheme.colorScheme.onSecondary
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
            StandingsDetailsBannerComponent(
                name = driverClickInfo?.givenName?: constructorClickInfo?.constructorName ?: "",
                description = driverClickInfo?.familyName?: constructorDetails?.chassis ?: "",
                season = driverClickInfo?.season?: constructorClickInfo?.season ?: "",
                position = driverClickInfo?.position?: constructorClickInfo?.position ?: "",
                points = driverClickInfo?.points?: constructorClickInfo?.points ?: "",
                wins = driverClickInfo?.wins?: constructorClickInfo?.wins ?: "",
                podiums = "s",
                poles = "2",
                dnf = "0",
                teamColor = teamColor
            )
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



