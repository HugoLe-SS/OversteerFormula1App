package com.hugo.result.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.design.R.drawable
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.result.R
import com.hugo.utilities.AppUtilities.toShortGPFormat

@Composable
fun CircuitResultBannerComponent(
    f1CalendarResult: List<F1CalendarRaceResult>?= null,
    circuitDetails: F1CircuitDetails? = null,
    circuitImg: Int? = null,
){

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            AppTheme.colorScheme.secondary.copy(alpha = 0.6f),
            AppTheme.colorScheme.background,
        )
    )

    val f1CalendarFirstResult = f1CalendarResult?.firstOrNull() ?: return

    val firstConstructorId = f1CalendarResult.getOrNull(0)?.constructorId ?: ""
    val secondConstructorId = f1CalendarResult.getOrNull(1)?.constructorId ?: ""
    val thirdConstructorId = f1CalendarResult.getOrNull(2)?.constructorId ?: ""

    val firstDriverColor = AppColors.Teams.colors[firstConstructorId] ?: AppTheme.colorScheme.onSecondary
    val secondDriverColor = AppColors.Teams.colors[secondConstructorId] ?: AppTheme.colorScheme.onSecondary
    val thirdDriverColor = AppColors.Teams.colors[thirdConstructorId] ?: AppTheme.colorScheme.onSecondary


    f1CalendarResult?.let {
        Row(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(brush = gradientBrush)
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
                    .padding(start = 12.dp)

            ) {
                //Round, RaceName, Podiums
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    Text(
                        text = "Round ${f1CalendarFirstResult.round}",
                        style = AppTheme.typography.labelNormal,
                        color = AppTheme.colorScheme.secondary,
                    )


                    Text(
                        text = f1CalendarFirstResult.raceName.toShortGPFormat(),
                        style = AppTheme.typography.titleNormal,
                        color = AppTheme.colorScheme.onSecondary
                    )

                    circuitDetails?.let{
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PodiumsDriverComponent(
                                modifier = Modifier.weight(1f),
                                driverIcon = R.drawable.ic_first,
                                driverName = circuitDetails.circuitPodiums?.firstOrNull() ?: "",
                                driverColor = firstDriverColor
                            )

                            PodiumsDriverComponent(
                                modifier = Modifier.weight(1f),
                                driverIcon = R.drawable.ic_second,
                                driverName = circuitDetails.circuitPodiums?.getOrNull(1) ?: "",
                                driverColor = secondDriverColor
                            )

                            PodiumsDriverComponent(
                                modifier = Modifier.weight(1f),
                                driverIcon = R.drawable.ic_third,
                                driverName = circuitDetails.circuitPodiums?.getOrNull(2) ?: "",
                                driverColor = thirdDriverColor
                            )
                        }
                    }

                }

                //Archive
                circuitDetails?.let {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CircuitResultArchive(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            archiveIcon = R.drawable.ic_fastest_lap,
                            archiveDetails = "${circuitDetails.fastestLaps?.firstOrNull() ?: "N/A" } " +
                                    "(${circuitDetails.fastestLaps?.getOrNull(1) ?: "N/A"})",
                            archiveDescription = stringResource(R.string.fastest_lap)
                        )

                        CircuitResultArchive(
                            modifier = Modifier.weight(1f),
                            archiveIcon = R.drawable.ic_dotd,
                            archiveDetails = circuitDetails.dotd ?: "N/A",
                            archiveDescription = stringResource(R.string.driver_of_the_day)
                        )

                        CircuitResultArchive(
                            modifier = Modifier.weight(1f),
                            archiveIcon = R.drawable.ic_tyre,
                            archiveDetails  = "${circuitDetails.fastestPit?.firstOrNull() ?: "N/A" } " +
                                    "(${circuitDetails.fastestPit?.getOrNull(1) ?: "N/A"})",
                            archiveDescription = stringResource(R.string.fastest_pitstop)
                        )
                    }
                }

            }


            //Image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ImageComponent(
                    imageResourceValue = circuitImg,
                    contentDescription = stringResource(R.string.banner),
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }


}

@Composable
fun PodiumsDriverComponent(
    modifier: Modifier = Modifier,
    driverIcon: Int? = null,
    driverName: String = "",
    driverColor: Color
){
    Row(
        modifier = modifier
    ){
        ImageComponent(
            modifier = Modifier
                .size(50.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
            imageResourceValue = driverIcon,
            contentDescription = stringResource(R.string.driver_icon),
            colorFilter = ColorFilter.tint(driverColor)

        )

        Text(
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically),
            text = driverName,
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colorScheme.onSecondary
        )
    }

}

@Composable
fun CircuitResultArchive(
    modifier: Modifier = Modifier,
    archiveIcon: Int? = null,
    archiveDescription: String = "",
    archiveDetails: String = ""
){
    Row(
        modifier = modifier
    ){
        ImageComponent(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .size(20.dp),
            imageResourceValue = archiveIcon,
            contentDescription = stringResource(R.string.archive_icon),
        )

        Column (
            modifier = Modifier
                .weight(5f)
                .fillMaxSize()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center,
        ){
            Text(
                text = archiveDetails,
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.onSecondary
            )
            Text(
                text = archiveDescription,
                style = AppTheme.typography.labelMini,
                color = AppTheme.colorScheme.secondary
            )
        }

    }

}

@Preview
@Composable
fun CircuitResultBannerComponentPreview(){
    AppTheme(isDarkTheme = true) {
        CircuitResultBannerComponent(
            circuitImg = drawable.circuit_monaco
        )
    }
}
