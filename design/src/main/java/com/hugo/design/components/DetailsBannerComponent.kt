package com.hugo.design.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
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
    driverImgUrl:String,
    buttonClicked: () -> Unit = {}
)
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(350.dp)
        .padding(12.dp)){

        Row(
            modifier = Modifier.weight(5f)
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

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


            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ImageComponent(
                    modifier = Modifier.weight(2f),
                    imageUrl = driverImgUrl,
                    contentDescription = "Banner",
                    contentScale = ContentScale.Fit
                )

                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    ){
                        //Wins
                        DetailsRow(
                            modifier = Modifier.weight(1f),
                            //icon = R.drawable.ic_trophy,
                            text = wins,
                            description = if(wins <= "1") " Win" else "Wins",
                            textColor = teamColor
                        )

                        //Podiums
                        DetailsRow(
                            modifier = Modifier.weight(1f),
                            //icon = R.drawable.ic_trophy,
                            text = podiums,
                            description = if(podiums <= "1") "Podium" else "Podiums",
                            textColor = teamColor
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    ){
                        //Poles
                        DetailsRow(
                            modifier = Modifier.weight(1f),
                            //icon = R.drawable.ic_trophy,
                            text = poles,
                            description = if(poles <= "1") "Pole" else "Poles",
                            textColor = teamColor
                        )

                        DetailsRow(
                            modifier = Modifier.weight(1f),
                            //icon = R.drawable.ic_trophy,
                            text = dnf,
                            description = if(dnf <= "1") "DNF" else "DNFs",
                            textColor = teamColor
                        )
                    }
                }

            }
        }

        ButtonComponent(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.End),
            text = stringResource(R.string.view_results),
            buttonClicked = buttonClicked
        )

    }


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
    circuitImgUrl: String,
    buttonClicked: () -> Unit = {}

)
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(350.dp)
        .padding(12.dp)){

        Row(
            modifier = Modifier.weight(5f)
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

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

                //circuit Name
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


            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ImageComponent(
                    modifier = Modifier.weight(2f),
                    imageUrl = circuitImgUrl,
                    contentDescription = "Banner",
                    contentScale = ContentScale.Fit
                )

                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    ){
                        //No. of laps
                        DetailsRow(
                            modifier = Modifier.weight(1f),
                            text = laps,
                            description = "Laps",
                            textColor = circuitColor
                        )

                        DetailsRow(
                            modifier = Modifier.weight(1f),
                            text = topSpeed,
                            description = "Top speed",
                            textColor = circuitColor
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    ){
                        //

                        DetailsRow(
                            modifier = Modifier.weight(1f),
                            //icon = R.drawable.ic_trophy,
                            text = turns,
                            description = "Turns",
                            textColor = circuitColor
                        )

                        DetailsRow(
                            modifier = Modifier.weight(1f),
                            text = elevation,
                            description = "Elevation",
                            textColor = circuitColor
                        )
                    }
                }

            }
        }

        ButtonComponent(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.End),
            text = stringResource(R.string.view_results),
            buttonClicked = buttonClicked
        )

    }

}

@Composable
fun DetailsRow(
    modifier: Modifier = Modifier,
    text: String,
    description: String,
    textColor: Color = AppTheme.colorScheme.onSecondary,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 4.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            style = AppTheme.typography.titleNormal,
            color = AppTheme.colorScheme.onSecondary
        )
        Text(
            text = description,
            style = AppTheme.typography.labelSmall,
            color = textColor
        )
    }
}

@Preview
@Composable
fun StandingsDetailsBannerComponentPreview(){
//    StandingsDetailsBannerComponent(
//        name = "Max Verstappen",
//        description = "Red Bull Racing",
//        season = "2023",
//        position = "1",
//        points = "400",
//        wins = "10",
//        podiums = "12",
//        poles = "2",
//        dnf = "0",
//        teamColor = AppTheme.colorScheme.onSecondary,
//        driverImgUrl = ""
//    )
    ScheduleDetailsBannerComponent(
        round = "1",
        raceName = "Australian GP",
        circuitName = "Albert Park Circuit",
        date = "2023-03-26",
        circuitLength = "5.278",
        laps = "58",
        turns = "16",
        topSpeed = "330",
        elevation = "0",
        circuitColor = AppTheme.colorScheme.onSecondary,
        circuitImgUrl = "",
    )
}