package com.hugo.oversteerf1.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.hugo.datasource.local.entity.F1News
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme

@Composable
fun NewsListItem(news: F1News){
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 450.dp)
                .wrapContentHeight()
                .clickable {
                    //
                }
                .padding(top = 18.dp, start = 18.dp, end = 18.dp)
                .border(1.dp, AppTheme.colorScheme.primary, RoundedCornerShape(10.dp)),

            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colorScheme.onSecondary
            )
        ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                ) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = "${news.title} ",
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.primary,
                    )
                    ImageComponent(
                        modifier = Modifier
                            .size(240.dp)
                    )

                }
        }


}

@Preview
@Composable
fun CardPreview(){

    NewsListItem(
        news = TODO()
    )
}

