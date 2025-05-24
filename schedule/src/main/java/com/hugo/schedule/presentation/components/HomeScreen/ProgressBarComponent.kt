package com.hugo.schedule.presentation.components.HomeScreen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.schedule.R
import com.hugo.utilities.AppUtilities


@Composable
fun ProgressBarComponent(
    countdown: AppUtilities.CountDownInfo?,
    color: Color
) {
    if (countdown?.status == "Live") {
        val progress = countdown.progress.coerceIn(0f, 1f)
        val logoSize = 32.dp
        val progressBarHeight = 8.dp

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {

            BoxWithConstraints(
                modifier = Modifier
                    .weight(7f)
                    .height(logoSize)
            ) {
                val barActualWidth = maxWidth
                val thumbOffsetX = (barActualWidth * progress) - (logoSize / 2)

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(progressBarHeight)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(4.dp)),
                    color = color,
                    trackColor = AppTheme.colorScheme.onSecondary
                )

                // 2. Logo slider thumb (drawn on top)
                ImageComponent(
                    modifier = Modifier
                        .size(logoSize)
                        .offset(x = thumbOffsetX)
                        .align(Alignment.CenterStart),
                    imageResourceValue = R.drawable.ic_f1_car,
                    contentDescription = "Progress Logo"
                )
            }

            // Chequered Flag
            ImageComponent(
                modifier = Modifier
                    .weight(1f)
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                imageResourceValue = R.drawable.ic_checkered_flag,
                contentDescription = "Chequered Flag"
            )
        }
    }
}


@Preview
@Composable
fun ProgressBarPreview(){
    AppTheme(isDarkTheme = true){
        ProgressBarComponent(
            countdown = AppUtilities.CountDownInfo(
                sessionName = "FP1",
                days = "",
                hours = "",
                minutes = "",
                status = "Live",
                progress = 0.1f
            ),
            color = AppTheme.colorScheme.primary
        )
    }
}