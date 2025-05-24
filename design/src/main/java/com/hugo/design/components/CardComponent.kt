package com.hugo.design.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.design.R
import com.hugo.design.ui.theme.AppTheme

@Composable
fun CardComponent(
    cardOnClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    elevation: CardElevation = CardDefaults.cardElevation(2.dp),
    color: CardColors = CardDefaults.cardColors(
        containerColor = AppTheme.colorScheme.background
    ),
    teamColor: Color,
    firstColumnDescription: String? = "",
    firstColumnDetails: String? = "",
    secondColumnDescription: String? = "",
    secondColumnDetails: String? = "",
    thirdColumnDescription: String? = "",
    thirdColumnDetails: String?= "",
    circuitImage: Int? = null,
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { cardOnClicked() }
            .padding(12.dp),
        elevation = elevation,
        colors = color
    ) {
        CardDetails(
            firstColumnDescription = firstColumnDescription,
            firstColumnDetails = firstColumnDetails,
            secondColumnDescription = secondColumnDescription,
            secondColumnDetails = secondColumnDetails,
            thirdColumnDescription = thirdColumnDescription,
            thirdColumnDetails = thirdColumnDetails,
            circuitImage = circuitImage,
            icon = R.drawable.ic_forward,
            teamColor = teamColor
        )
    }
}

@Composable
fun CardDetails(
    firstColumnDescription: String? = "",
    firstColumnDetails: String? = "",
    secondColumnDescription: String? = "",
    secondColumnDetails: String? = "",
    thirdColumnDescription: String? = "",
    thirdColumnDetails: String?= "",
    circuitImage: Int? = null,
    icon: Int? = null,
    teamColor: Color
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            //Column 1
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
            ) {
                firstColumnDescription?.also {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        textAlign = TextAlign.Center,
                        text = it,
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.onSecondary,
                    )
                }

                firstColumnDetails?.also {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        textAlign = TextAlign.Center,
                        text = it,
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.onSecondary,
                    )
                }

            }

            //Column 2
            Column(
                modifier = Modifier
                    .weight(6f)
                    .padding(start = 8.dp),
            ) {
                secondColumnDescription?.also{
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(),
                        textAlign = TextAlign.Center,
                        text = it,
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.onSecondary,
                    )
                }

                secondColumnDetails?.also{
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(),
                        textAlign = TextAlign.Center,
                        text = it,
                        style = AppTheme.typography.labelNormal,
                        color = teamColor,
                    )
                }

            }

            //Column 3
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if(circuitImage != null){
                    ImageComponent(
                        contentScale = ContentScale.Fit,
                        imageResourceValue = circuitImage,
                        contentDescription = "Circuit Image",
                    )
                }
                else{
                    thirdColumnDescription?.also{
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentSize(),
                            textAlign = TextAlign.Center,
                            text = it,
                            style = AppTheme.typography.body,
                            color = AppTheme.colorScheme.onSecondary,
                        )
                    }

                    thirdColumnDetails?.also{
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentSize(),
                            textAlign = TextAlign.Center,
                            text = it,
                            style = AppTheme.typography.labelNormal,
                            color = AppTheme.colorScheme.onSecondary,
                        )
                    }
                }

            }

            //Column 4 / Icon
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                ImageComponent(
                    modifier = Modifier
                        .size(24.dp),
                    imageResourceValue = icon,
                    contentDescription = "Icon",
                )

            }
        }
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardComponentPreview() {
    AppTheme(isDarkTheme = true) {
        CardComponent(
            firstColumnDescription = "16",
            firstColumnDetails = "Mar",
            secondColumnDescription = "Constructor",
            secondColumnDetails = "Mercedes",
            circuitImage = R.drawable.flag_belgium,
            thirdColumnDescription = "Points",
            thirdColumnDetails = "PTS",
            teamColor = Color.Cyan
        )
    }
}