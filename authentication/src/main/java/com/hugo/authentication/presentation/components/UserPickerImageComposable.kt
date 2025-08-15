package com.hugo.authentication.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hugo.authentication.R
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme

@Composable
fun UserImagePickerBox(
    imageUri: Uri?,
    userAvatarUrl: String?,
    onBoxClicked: () -> Unit
) {

    val shape = RoundedCornerShape(16.dp)
    val imageModel = imageUri ?: userAvatarUrl

    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(shape)
            .border(2.dp, Color.White, shape) // white border
            .clickable {
                onBoxClicked()
            }
            .background(AppTheme.colorScheme.onBackground.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        if (imageModel != null) {
            ImageComponent(
                imageUrl = imageModel.toString(),
                contentDescription = "User Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),
                contentScale = ContentScale.Crop
            )
        } else {
            //Text("Tap to Upload", color = Color.White)
            ImageComponent(
                imageUrl = userAvatarUrl,
                contentDescription = "User Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.BottomEnd
        ){
            ImageComponent(
                imageResourceValue = R.drawable.ic_camera
            )
        }

    }
}