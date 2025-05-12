package com.hugo.standings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.design.components.CardComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo

@Composable
fun ConstructorListItem(
    constructor: ConstructorStandingsInfo,
    constructorCardClicked: (ConstructorClickInfo) -> Unit = {}
) {

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
        secondColumnDetails = "${constructor.constructorName} ",
        secondColumnDescription = "${constructor.constructorNationality} ",
        thirdColumnDescription= "${constructor.points} ",
        thirdColumnDetails = "PTS ",
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
        secondColumnDetails = "${driver.driverGivenName} ${driver.driverLastName} ",
        secondColumnDescription = "${driver.constructorName} ",
        thirdColumnDescription= "${driver.points} ",
        thirdColumnDetails = "PTS ",
    )

    Spacer(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(AppTheme.colorScheme.onBackground)
    )

}