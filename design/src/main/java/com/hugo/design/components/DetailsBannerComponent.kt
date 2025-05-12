package com.hugo.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.hugo.design.R
import com.hugo.design.ui.theme.AppTheme

@Composable
fun StandingsDetailsBannerComponent(
    name: String,
    description: String,
    season: String,
    position: String,
    points: String,
    wins: String,
    podiums: String,
    poles: String,
    dnf: String,
    teamColor: Color,
)
{
    Text(
        text = name,
        style = AppTheme.typography.titleLarge,
        color = AppTheme.colorScheme.onSecondary
    )

    Text(
        text = description,
        style = AppTheme.typography.titleNormal,
        color = teamColor
    )

    //Season
    Text(
        text = "$season season",
        style = AppTheme.typography.labelSmall,
        color = AppTheme.colorScheme.onSecondary,
    )

    //Position
    Text(
        text = buildAnnotatedString {
            append("$position ")
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
            append("$points ")
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

    //Wins
    DetailsRow(
        icon = R.drawable.ic_trophy,
        text = wins,
        description = if(wins <= "1") " Win" else "Wins"
    )

    //Podiums
    DetailsRow(
        icon = R.drawable.ic_trophy,
        text = podiums,
        description = if(podiums <= "1") "Podium" else "Podiums"
    )

    //Poles
    DetailsRow(
        icon = R.drawable.ic_trophy,
        text = poles,
        description = if(poles <= "1") "Pole" else "Poles"
    )

   DetailsRow(
       icon = R.drawable.ic_trophy,
       text = dnf,
       description = if(dnf <= "1") "DNF" else "DNFs"
    )
}

@Composable
fun ScheduleDetailsBannerComponent(
    round: String,
    raceName: String,
    circuitName: String,
    date: String,
    circuitLength: String,
    laps: String,
    turns: String,
    topSpeed: String,
    elevation: String,
    circuitColor: Color,
)
{
    //Round
    Text(
        text = "Round $round ",
        style = AppTheme.typography.labelSmall,
        color = AppTheme.colorScheme.onSecondary,
    )

    Text(
        text = raceName,
        style = AppTheme.typography.titleLarge,
        color = AppTheme.colorScheme.onSecondary
    )

    Text(
        text = circuitName,
        style = AppTheme.typography.titleNormal,
        color = circuitColor
    )

    //date
    Text(
        text = date,
        style = AppTheme.typography.labelSmall,
        color = AppTheme.colorScheme.onSecondary,
    )

    //Length
    Text(
        text = buildAnnotatedString {
            append("$circuitLength ")
            withStyle(
                style = AppTheme.typography.labelNormal.toSpanStyle().copy(
                    color = circuitColor
                )
            ) {
                append("KM")
            }
        },
        style = AppTheme.typography.titleLarge,
        color = AppTheme.colorScheme.onSecondary
    )


    //No. of laps
    DetailsRow(
        icon = R.drawable.ic_trophy,
        text = laps,
        description = "Laps"
    )

    DetailsRow(
        icon = R.drawable.ic_trophy,
        text = turns,
        description = "Turns"
    )

    //
    DetailsRow(
        icon = R.drawable.ic_trophy,
        text = topSpeed,
        description = "Top Speed"
    )


    DetailsRow(
        icon = R.drawable.ic_trophy,
        text = elevation,
        description = "Elevation"
    )
}

@Composable
fun DetailsRow(
    icon: Int,
    text: String,
    description: String
){
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageComponent(
            imageResourceValue = icon,
            contentDescription = "icon",
            modifier = Modifier
                .size(24.dp)
                .padding(end = 12.dp)
        )
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = text,
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.onSecondary
            )
            Text(
                text = description,
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.onSecondary
            )
        }

    }
}
