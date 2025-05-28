package com.hugo.standings.presentation.components.StandingsDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.design.components.StandingsDetailsBannerComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
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

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            teamColor.copy(alpha = 0.6f),
            AppTheme.colorScheme.background,
        )
    )

    Box(
        modifier = Modifier
            .background(brush = gradientBrush)
    ) {

            StandingsDetailsBannerComponent(
                name = driverClickInfo?.givenName?: constructorClickInfo?.constructorName ?: "",
                description = driverClickInfo?.familyName?: constructorDetails?.chassis ?: "",
                season = driverClickInfo?.season?: constructorClickInfo?.season ?: "",
                position = driverClickInfo?.position?: constructorClickInfo?.position ?: "",
                points = driverClickInfo?.points?: constructorClickInfo?.points ?: "",
                wins = driverClickInfo?.wins?: constructorClickInfo?.wins ?: "",
                podiums = driverDetails?.driverStats?.get(0) ?: constructorDetails?.constructorStats?.get(0) ?: "",
                poles = driverDetails?.driverStats?.get(1) ?: constructorDetails?.constructorStats?.get(1) ?: "",
                dnf = driverDetails?.driverStats?.get(2) ?: constructorDetails?.constructorStats?.get(2) ?: "",
                teamColor = teamColor,
                driverImgUrl = driverDetails?.imageUrl ?: constructorDetails?.imageUrl ?: "",
                buttonClicked = {
                    buttonClicked(driverClickInfo?.driverId ?:
                    constructorClickInfo?.constructorId ?: "")
                }
            )
        }


}



