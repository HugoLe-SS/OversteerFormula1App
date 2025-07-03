package com.hugo.account.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.profile.R

enum class ProfileCardType {
    MyAccount,
    Notifications,
    AppSettings,
    Feedback,
    LegalPrivacy
}

@Composable
fun ProfileScreenCardList(
    cardOnClicked: (ProfileCardType) -> Unit = {}
) {
    val cardItems = listOf(
        ProfileCardType.MyAccount to R.drawable.ic_person,
        ProfileCardType.Notifications to R.drawable.ic_noti,
        ProfileCardType.AppSettings to R.drawable.ic_settings,
        ProfileCardType.Feedback to R.drawable.ic_feedback,
        ProfileCardType.LegalPrivacy to R.drawable.ic_legal,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
            .border(
                width = 1.dp,
                color = AppTheme.colorScheme.onSecondary.copy(alpha = 0.1f),
                shape = AppTheme.shape.container
            )
            .background(
                color = Color.Transparent,
                shape = AppTheme.shape.container
            )
    ) {
        cardItems.forEachIndexed { index, (type, icon) ->
            ProfileScreenCardComponent(
                cardText = type.name.replace(Regex("([a-z])([A-Z])"), "$1 $2"),
                icon = icon,
                cardOnClicked = { cardOnClicked(type) }
            )

            if (index < cardItems.lastIndex) {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .padding(horizontal = 12.dp)
                        .background(AppTheme.colorScheme.onSecondary.copy(alpha = 0.1f))
                )
            }
        }
    }
}

@Composable
fun ProfileScreenCardComponent(
    cardText : String = "",
    icon: Int,
    cardOnClicked: () -> Unit = {}
){
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                cardOnClicked()
            }
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .padding(start = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    ImageComponent(
                        imageResourceValue = icon,
                        contentDescription = "Profile Card Icon",
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxSize()
                        .padding(start = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    Text(
                        text = cardText,
                        style = AppTheme.typography.labelSmall,
                        color = AppTheme.colorScheme.onSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxSize()
                        .padding(end = 12.dp),
                    contentAlignment = Alignment.CenterEnd
                ){
                    ImageComponent(
                        imageResourceValue = com.hugo.design.R.drawable.ic_forward
                    )
                }
            }


        }


    }

}

@Preview
@Composable
fun ProfileScreenCardComponentPreview(){
    AppTheme(isDarkTheme = true){
//        ProfileScreenCardComponent(
//            cardText = "My Account",
//            icon = com.hugo.design.R.drawable.ic_noti
//        )
        ProfileScreenCardList()
    }
}
