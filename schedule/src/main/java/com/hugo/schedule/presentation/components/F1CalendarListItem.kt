package com.hugo.schedule.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.com.hugo.utilities.Navigation.model.CalendarClickInfo

//@Composable
//fun F1CalendarListItem(
//    calendar: F1CalendarInfo,
//    cardClicked: (CalendarClickInfo) -> Unit = {}
//    //cardClicked: (String) -> Unit = {}
//){
//    Card (
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)
//            .wrapContentHeight()
//            .clickable {
//                cardClicked(
//                    CalendarClickInfo(
//                    calendar.round,
//                    calendar.circuitId
//                )
//                )
//                //cardClicked(calendar.round)
//            }
//            .padding(12.dp),
//
//        elevation = CardDefaults.cardElevation(2.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = AppTheme.colorScheme.background
//        )
//    ){
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp),
//        ) {
//            //row 1
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 4.dp),
//            ){
//                Text(
//                    modifier = Modifier
//                        //.wrapContentSize()
//                        .weight(2f),
//                    //textAlign = TextAlign.Center,
//                    text = "${calendar.mainRaceDate} ",
//                    style = AppTheme.typography.body,
//                    color = AppTheme.colorScheme.onSecondary,
//                )
//
//                Text(
//                    modifier = Modifier
//                        //.wrapContentSize()
//                        .weight(8f),
//                    //textAlign = TextAlign.Center,
//                    text = "${calendar.raceName} ",
//                    style = AppTheme.typography.body,
//                    color = AppTheme.colorScheme.onSecondary,
//                )
//
//                //Track Icon
//                ImageComponent(
//
//                )
//
//            }
//
//            //row 2
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ){
//                Spacer(modifier = Modifier.weight(2f))
//
//                Text(
//                    modifier = Modifier
//                        //.wrapContentSize()
//                        .weight(8f),
//                    //textAlign = TextAlign.Center,
//                    text = "Round ${calendar.round} ",
//                    style = AppTheme.typography.labelSmall,
//                    color = AppTheme.colorScheme.onSecondary,
//                )
//
//                Text(
//                    modifier = Modifier
//                        .wrapContentSize()
//                        .weight(4f),
//                    textAlign = TextAlign.Center,
//                    text = "PTS ",
//                    style = AppTheme.typography.labelSmall,
//                    color = AppTheme.colorScheme.onSecondary,
//                )
//
//            }
//        }
//
//        Spacer(
//            Modifier
//                .fillMaxWidth()
//                .height(2.dp)
//                .background(AppTheme.colorScheme.onBackground)
//        )
//
//    }
//}


@Composable
fun F1CalendarListItem(
    calendar: F1CalendarInfo,
    cardClicked: (CalendarClickInfo) -> Unit = {}
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .wrapContentHeight()
            .clickable {
                cardClicked(
                    CalendarClickInfo(
                        calendar.round,
                        calendar.circuitId
                    )
                )
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
                    text = "${calendar.mainRaceDate} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(8f),
                    //textAlign = TextAlign.Center,
                    text = "${calendar.raceName} ",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onSecondary,
                )

                Text(
                    modifier = Modifier
                        //.wrapContentSize()
                        .weight(4f),
                    textAlign = TextAlign.Center,
                    text = "${calendar.round} ",
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
                    text = "${calendar.circuit} ",
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
