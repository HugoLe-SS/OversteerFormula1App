package com.hugo.result.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.result.R
import com.hugo.utilities.AppUtilities

@Composable
fun ResultListItem(
    driverRace: DriverRaceResultsInfo? = null,
    driverQuali: DriverQualifyingResultsInfo? = null,
    constructorRace: ConstructorRaceResultsInfo? = null,
    constructorQuali: ConstructorQualifyingResultsInfo? = null,
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
        if(driverRace != null && driverQuali != null)
        {
            DriverRaceResultListItem(
                driverRace = driverRace, driverQuali = driverQuali
            )
        }
        else if(constructorRace != null && constructorQuali != null)
        {
            ConstructorRaceResultListItem(
                constructorRace = constructorRace, constructorQuali = constructorQuali
            )
        }

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

@Composable fun ConstructorRaceResultListItem(
    constructorRace: ConstructorRaceResultsInfo,
    constructorQuali: ConstructorQualifyingResultsInfo,
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
                text = "${constructorRace.position} ",
                style = AppTheme.typography.body,
                color = AppTheme.colorScheme.onSecondary,
            )

            Text(
                modifier = Modifier
                    //.wrapContentSize()
                    .weight(8f),
                //textAlign = TextAlign.Center,
                text = "${constructorRace.circuitName} ",
                style = AppTheme.typography.body,
                color = AppTheme.colorScheme.onSecondary,
            )

            Text(
                modifier = Modifier
                    //.wrapContentSize()
                    .weight(4f),
                textAlign = TextAlign.Center,
                text = "${constructorRace.points} ",
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
                    grid = constructorRace.grid,
                    position = constructorRace.position
                )
            }

            Text(
                modifier = Modifier
                    //.wrapContentSize()
                    .weight(8f),
                //textAlign = TextAlign.Center,
                text = "${constructorRace.country} ",
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
            contentDescription = stringResource(R.string.arrow_up_icon),
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
            contentDescription = stringResource(R.string.arrow_down_icon),
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