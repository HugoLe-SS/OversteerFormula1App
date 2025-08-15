package com.hugo.standings.presentation.components.StandingsHomeScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.design.components.CardComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo

@Composable
fun ConstructorListItem(
    constructor: ConstructorStandingsInfo,
    constructorCardClicked: (ConstructorClickInfo) -> Unit = {}
) {

    val teamColor = AppColors.Teams.colors[constructor.constructorId] ?: AppTheme.colorScheme.onSecondary

    CardComponent(
        cardOnClicked = {
            constructorCardClicked(
                ConstructorClickInfo(
                    constructorId = constructor.constructorId,
                    constructorName = constructor.constructorName,
                    season = constructor.season,
                    nationality = constructor.constructorNationality,
                    position = constructor.position,
                    points = constructor.points,
                    wins = constructor.wins
                )

            )
        },
        firstColumnDescription = "${constructor.position} ",
        firstColumnDetails = "",
        secondColumnDescription = "${constructor.constructorName} ",
        secondColumnDetails = "${constructor.constructorNationality} ",
        thirdColumnDescription= "${constructor.points} ",
        thirdColumnDetails = "PTS ",
        teamColor = teamColor
    )
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(AppTheme.colorScheme.onBackground)
    )

}

@Composable
fun Modifier.shimmerLoading(
    durationMillis: Int = 1000,
): Modifier {
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "",
    )

    return drawBehind {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.2f),
                    Color.LightGray.copy(alpha = 1.0f),
                    Color.LightGray.copy(alpha = 0.2f),
                ),
                start = Offset(x = translateAnimation, y = translateAnimation),
                end = Offset(x = translateAnimation + 100f, y = translateAnimation + 100f),
            )
        )
    }
}

@Composable
fun StandingsListItemSkeletonLoader(
    modifier: Modifier = Modifier
) {
    CardComponent(
        cardOnClicked = {},
        teamColor = Color.Transparent,
        modifier = Modifier
    )
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(AppTheme.colorScheme.onBackground)
    )

}


@Composable
fun DriverListItem(
    driver: DriverStandingsInfo,
    driverCardClicked: (DriverClickInfo) -> Unit = {}
){
    val teamColor = AppColors.Teams.colors[driver.constructorId] ?: AppTheme.colorScheme.onSecondary

    CardComponent(
        cardOnClicked = {
            driverCardClicked(
                DriverClickInfo(
                    driverId = driver.driverId,
                    constructorName = driver.constructorName,
                    constructorId = driver.constructorId,
                    season = driver.season,
                    givenName = driver.driverGivenName,
                    familyName = driver.driverLastName,
                    driverNumber = driver.driverNumber,
                    driverCode = driver.driverCode,
                    position = driver.position,
                    points = driver.points,
                    wins = driver.wins
                )
            )
        },
        firstColumnDescription = "${driver.position} ",
        firstColumnDetails = "",
        secondColumnDescription = "${driver.driverGivenName} ${driver.driverLastName} ",
        secondColumnDetails = "${driver.constructorName} ",
        thirdColumnDescription= "${driver.points} ",
        thirdColumnDetails = "PTS ",
        teamColor = teamColor
    )

    Spacer(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(AppTheme.colorScheme.onBackground)
    )

}