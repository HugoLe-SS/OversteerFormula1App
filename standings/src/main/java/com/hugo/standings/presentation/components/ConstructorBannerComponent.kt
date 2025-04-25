package com.hugo.standings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.R

@Composable
fun ConstructorBannerComponent(
    driverImgUrl: String,
    teamImgUrl: String,
    firstDriverGivenName: String,
    firstDriverLastName: String,
    secondDriverGivenName: String,
    secondDriverLastName: String,
    firstDriverNumber: String,
    secondDriverNumber: String,
    teamName: String,
    gradientBrush: Brush = Brush.horizontalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            AppColors.Teams.mclaren
        )
    ),
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(brush = gradientBrush)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
            ){

                Column(
                    modifier = Modifier.weight(1f)
                ){
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(),
                        text =  "Championship Leader",
                        style = AppTheme.typography.body,
                        color = AppColors.Teams.mclaren,
                    )


                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            modifier = Modifier
                                .wrapContentSize(),
                            text =  teamName,
                            style = AppTheme.typography.labelSmall,
                            color = AppTheme.colorScheme.onSecondary,
                        )

//                        ImageComponent(
//                            modifier = Modifier.size(24.dp),
//                            imageUrl = teamImgUrl,
//                            contentDescription = "Team Logo",
//                            contentScale = ContentScale.Fit,
//                        )
                        ImageComponent(
                            modifier = Modifier.size(24.dp),
                            imageResourceValue = com.hugo.design.R.drawable.ic_mclaren,
                            contentDescription = "Team Logo",
                            contentScale = ContentScale.Fit,
                        )

                    }
                }


                Spacer(
                    Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(brush = gradientBrush)
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column (
                        modifier = Modifier.weight(3f)
                    ){
                        Text(
                            text = firstDriverGivenName,
                            style = AppTheme.typography.titleNormal,
                            color = AppTheme.colorScheme.onSecondary,
                        )

                        Text(
                            text = firstDriverLastName,
                            style = AppTheme.typography.titleLarge,
                            color = AppColors.Teams.mclaren,
                        )
                    }

                    Spacer(
                        Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(brush = gradientBrush)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        //.padding(start = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = firstDriverNumber,
                            style = AppTheme.typography.titleLarge,
                            color = AppColors.Teams.mclaren,
                            fontSize = 28.sp
                        )
                    }

                }

                Spacer(
                    Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(brush = gradientBrush)
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column (
                        modifier = Modifier.weight(3f)
                    ){
                        Text(
                            text = secondDriverGivenName,
                            style = AppTheme.typography.titleNormal,
                            color = AppTheme.colorScheme.onSecondary,

                        )

                        Text(
                            text = secondDriverLastName,
                            style = AppTheme.typography.titleLarge,
                            color = AppColors.Teams.mclaren,
                        )
                    }

                    Spacer(
                        Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .background(brush = gradientBrush)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        //.padding(start = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = secondDriverNumber,
                            style = AppTheme.typography.titleLarge,
                            color = AppColors.Teams.mclaren,
                            fontSize = 28.sp
                        )
                    }
                }

            }


//            ImageComponent(
//                modifier = Modifier.weight(1f),
//                imageUrl = driverImgUrl,
//                contentDescription = "Driver Banner",
//                contentScale = ContentScale.Fit
//            )
            ImageComponent(
                modifier = Modifier.weight(1f),
                imageResourceValue =  R.drawable.ic_norris,
                contentDescription = "Driver Banner",
                contentScale = ContentScale.Crop
            )
        }
    }

}



@Preview(showBackground = true, showSystemUi = true,)
@Composable
fun ConstructorBannerComponent() {
    AppTheme(isDarkTheme = true)
    {
        ConstructorBannerComponent(
            firstDriverGivenName = "Lando",
            firstDriverLastName = "Norris",
            firstDriverNumber = "77",
            secondDriverNumber = "81",
            secondDriverGivenName = "Oscar",
            secondDriverLastName = "Piastri",
            teamName = "McLaren",
            driverImgUrl = "https://mclaren.bloomreach.io/delivery/resources/content/gallery/mclaren-racing/formula-1/2025/nsr/f1-75-live-m/web/2025_lando_team_pic_02.jpg",
            teamImgUrl = "https://media.formula1.com/content/dam/fom-website/teams/2025/mclaren-logo.png"
        )
    }

}