package com.hugo.standings.presentation.components.Driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.standings.R

@Composable
fun StandingsBannerComponent(
    driverInfo: DriverStandingsInfo? = null,
    constructorInfo: ConstructorStandingsInfo? = null,
    imageUrl: Int,
){
    val teamColor = AppColors.Teams.colors[driverInfo?.constructorId ?:constructorInfo?.constructorId] ?: AppTheme.colorScheme.onSecondary

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            teamColor.copy(alpha = 0.6f),
            teamColor.copy(alpha = 0.6f),
            AppTheme.colorScheme.background,
        )
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
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
                        .padding(start = 12.dp),
                ){

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                //.weight(1f)
                                .wrapContentSize(),
                            text = stringResource(R.string.championship_leader),
                            style = AppTheme.typography.body,
                            color = teamColor,
                        )

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp))

                        constructorInfo?.let {
                            Row(
                                modifier = Modifier
                                    //.weight(1f)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    modifier = Modifier.weight(3f),
                                    text = it.constructorName ?: "",
                                    style = AppTheme.typography.titleLarge,
                                    color = teamColor,
                                )

                                Box(
                                    modifier = Modifier
                                        .weight(1f),
                                ) {
                                    ImageComponent(
                                        //modifier = Modifier.size(80.dp),
                                        imageResourceValue = R.drawable.ic_mclaren,
                                        contentDescription = stringResource(R.string.team_logo),
                                        contentScale = ContentScale.Fit,
                                    )
                                }
                            }
                        }

                        driverInfo?.let {
                            Row(
                                modifier = Modifier
                                    //.weight(1f)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(
                                    Modifier.weight(3f)
                                ) {
                                    Text(
                                        text = driverInfo.driverGivenName ?: "",
                                        style = AppTheme.typography.titleNormal,
                                        color = AppTheme.colorScheme.onSecondary,
                                    )

                                    Text(
                                        text = driverInfo.driverLastName ?: "",
                                        style = AppTheme.typography.titleLarge,
                                        color = teamColor,
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f),
                                ) {
                                    Text(
                                        text = it.driverNumber,
                                        style = AppTheme.typography.titleLarge,
                                        color = teamColor,
                                        fontSize = 28.sp
                                    )
                                }
                            }
                        }
                    }
                }

                ImageComponent(
                    modifier = Modifier.weight(1f),
                    imageResourceValue = imageUrl,
                    contentDescription = "Banner",
                    contentScale = ContentScale.Fit
            )
        }
    }

}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DriverBannerComponentPreview() {
    AppTheme(isDarkTheme = true){
        StandingsBannerComponent(
            imageUrl = R.drawable.mclaren
        )
    }

}