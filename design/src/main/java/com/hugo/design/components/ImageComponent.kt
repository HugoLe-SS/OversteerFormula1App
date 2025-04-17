package com.hugo.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    imageResourceValue: Int?= null,
    imageUrl: String? = null
){
    imageResourceValue?.also{
        Image(
            modifier = modifier,
            painter = painterResource(id = imageResourceValue),
            contentDescription = "Image"
        )
    }

    if (imageUrl != null) {
        AsyncImage(
            modifier = modifier.fillMaxSize(),
            model = imageUrl,
            contentDescription = "Image",
            contentScale = ContentScale.Fit
        )
    }
}