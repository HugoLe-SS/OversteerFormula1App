package com.hugo.standings.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
    constructorCardClicked: () -> Unit = {}
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 450.dp)
            .wrapContentHeight()
            .clickable {
                constructorCardClicked()
            }
            .padding(top = 18.dp, start = 18.dp, end = 18.dp)
            .border(1.dp, AppTheme.colorScheme.primary, RoundedCornerShape(10.dp)),

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
    }


}

@Composable
fun DriverListItem(
    driver: DriverStandingsInfo,
    driverCardClicked: () -> Unit = {}
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 450.dp)
            .wrapContentHeight()
            .clickable {
                driverCardClicked()
            }
            .padding(top = 18.dp, start = 18.dp, end = 18.dp)
            .border(1.dp, AppTheme.colorScheme.primary, RoundedCornerShape(10.dp)),

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
                    text = "${driver.driverName} ",
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
    }


}