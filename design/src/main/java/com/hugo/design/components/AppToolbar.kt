package com.hugo.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.design.R
import com.hugo.design.ui.theme.AppTheme

@Composable
fun AppToolbar(
    title: String? = null,
    isBackButtonVisible: Boolean = false,
    isHomepage: Boolean = false,
    isSchedulePage: Boolean = false,
    isStandingsPage: Boolean = false,
    backButtonClicked: () -> Unit = {},
    backgroundColor : Color = Color.Black
){
    Column(
        modifier = Modifier
            .height(90.dp)
            .fillMaxSize()
            .systemBarsPadding()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .padding(12.dp),

        ){
            if(isBackButtonVisible){
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { backButtonClicked() },
                    painter = painterResource(id = R.drawable.ic_back_button),
                    contentDescription = "Back Button",
                    tint = Color.White
                )
            }
            else if(isHomepage) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "User Icon",
                    tint = Color.White
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_noti),
                    contentDescription = "Notification Icon",
                    tint = Color.White
                )

            }
            else if(isSchedulePage){
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Text(
                        text = stringResource(R.string.schedule),
                        textAlign = TextAlign.Center,
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.onPrimary,
                    )
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = AppTheme.colorScheme.onBackground,
                        //tonalElevation = 6.dp,
                    ) {
                        Row(
                        ) {
                            ButtonComponent(
                                text = "Upcoming",
                                buttonColor = Color.Transparent,
                            )
                            ButtonComponent(
                                text = "Past",
                                buttonColor = Color.Transparent
                            )
                        }
                    }


                }


            }
            else if(isStandingsPage){
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Text(
                        text = stringResource(R.string.standings),
                        textAlign = TextAlign.Center,
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.onPrimary,
                    )
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = AppTheme.colorScheme.onBackground,
                        //tonalElevation = 6.dp,
                    ) {
                        Row(
                        ) {
                            ButtonComponent(
                                text = "Upcoming",
                                buttonColor = Color.Transparent,
                            )
                            ButtonComponent(
                                text = "Past",
                                buttonColor = Color.Transparent
                            )
                        }
                    }


                }


            }
        }
    }

}

@Preview
@Composable
fun AppToolbarPreview() {
    AppTheme{
        AppToolbar(
            title = "Title",
            isBackButtonVisible = false,
            isSchedulePage = true,
            isHomepage = false,
            backButtonClicked = {}
        )
    }

}