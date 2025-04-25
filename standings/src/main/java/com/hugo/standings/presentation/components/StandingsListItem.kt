package com.hugo.standings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.domain.model.ConstructorStandingsInfo
import com.hugo.standings.domain.model.DriverStandingsInfo

@Composable
fun ConstructorListItem(
    constructor: ConstructorStandingsInfo,
    constructorCardClicked: (String) -> Unit = {}
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .wrapContentHeight()
            .clickable {
                constructorCardClicked(constructor.constructorId)
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
                .padding(12.dp),
        ) {
            //row 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
            ){
                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(2f),
                    //textAlign = TextAlign.Center,
                    text = "${constructor.position} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(8f),
                    //textAlign = TextAlign.Center,
                    text = "${constructor.constructorName} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(4f),
                    textAlign = TextAlign.Center,
                    text = "${constructor.points} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

            }

            //row 2
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Spacer(modifier = Modifier.weight(2f))

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(8f),
                    //textAlign = TextAlign.Center,
                    text = "${constructor.constructorNationality} ",
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
fun DriverListItem(
    driver: DriverStandingsInfo,
    driverCardClicked: (String) -> Unit = {}
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .wrapContentHeight()
            .clickable {
                driverCardClicked(driver.driverId)
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
                .padding(12.dp),
        ) {
            //row 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
            ){
                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(2f),
                    //textAlign = TextAlign.Center,
                    text = "${driver.position} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(8f),
                    //textAlign = TextAlign.Center,
                    text = "${driver.driverGivenName + " " + driver.driverLastName} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(4f),
                    textAlign = TextAlign.Center,
                    text = "${driver.points} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

            }

            //row 2
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Spacer(modifier = Modifier.weight(2f))

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(8f),
                    //textAlign = TextAlign.Center,
                    text = "${driver.constructorName} ",
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