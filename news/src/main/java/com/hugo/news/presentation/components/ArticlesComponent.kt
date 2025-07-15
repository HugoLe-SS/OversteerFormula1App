package com.hugo.news.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.news.domain.model.NewsInfo
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun ArticlesList(
    newsInfo: NewsInfo,
    onArticleClick: (String) -> Unit = {}
) {
    ArticlesComponent(
        headline = newsInfo.headline,
        description = newsInfo.description,
        byline = newsInfo.byline,
        published = newsInfo.published,
        imageUrl = newsInfo.imageUrl,
        webUrl = newsInfo.webUrl,
        onArticleClick = onArticleClick
    )

}

@Composable
fun ArticlesComponent(
    headline: String?,
    description: String?,
    byline: String?,
    published: String?,
    imageUrl: String?,
    webUrl: String,
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit = {}

) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onArticleClick(webUrl) },
            //.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Article Image
            imageUrl?.let { url ->

                ImageComponent(
                    imageUrl = url,
                    contentDescription = "Article image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Headline
            headline?.let{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ){
                    Text(
                        text = headline,
                        style = AppTheme.typography.titleNormal,
                        color = AppTheme.colorScheme.onSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 24.sp,
                    )
                }
            }



            Spacer(modifier = Modifier.height(8.dp))

            // Description
            description?.let { desc ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ){
                    Text(
                        text = desc.trim(),
                        style = AppTheme.typography.labelSmall,
                        color = AppTheme.colorScheme.onSecondary,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp
                    )
                }


                Spacer(modifier = Modifier.height(12.dp))
            }

            // Byline and Published Date Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Byline
                byline?.let { author ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = author,
                            style = AppTheme.typography.labelMini,
                            color = AppTheme.colorScheme.secondary,
                        )
                    }
                }

                // Published Date
                published?.let { date ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = formatPublishedDate(date),
                            style = AppTheme.typography.labelMini,
                            color = AppTheme.colorScheme.secondary,
                        )
                    }
                }
            }
        }
    }

}

private fun formatPublishedDate(dateString: String): String {
    return try {
        val instant = Instant.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        instant.atZone(ZoneId.systemDefault()).format(formatter)
    } catch (e: Exception) {
        "Unknown date"
    }
}

@Preview
@Composable
fun ArticlesComponentPreview() {
    AppTheme(
        isDarkTheme = true
    ){
        ArticlesComponent(
            headline = "Christian Horner exit: Laurent Mekies admits challenge awaits for Red Bull",
            description = "New Red Bull boss Laurent Mekies said it felt \"a bit unreal\"...\n",
            byline = "Nate Saunders",
            published = "2025-07-11T10:47:00Z",
            imageUrl = "https://a.espncdn.com/photo/2025/0711/r1517617_1296x729_16-9.jpg",
            webUrl = "https://www.espn.co.uk/f1/story/_/id/45715278/christian-horner-exit-laurent-mekies-admits-challenge-awaits-red-bull"
        )
    }
}