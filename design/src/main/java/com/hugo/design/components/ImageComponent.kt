package com.hugo.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    imageResourceValue: Int?= null,
    imageUrl: String? = null,
    contentDescription: String = "Image",
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter ?= null
){
    imageResourceValue?.also{
        Image(
            modifier = modifier,
            painter = painterResource(id = imageResourceValue),
            contentDescription = contentDescription,
            contentScale = contentScale,
            colorFilter = colorFilter
        )
    }

    if (imageUrl != null) {
        AsyncImage(
            modifier = modifier.fillMaxSize(),
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = contentScale,
            colorFilter = colorFilter
        )
    }
}