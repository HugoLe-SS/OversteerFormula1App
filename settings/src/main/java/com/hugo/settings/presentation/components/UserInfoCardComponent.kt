package com.hugo.settings.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme

@Composable
fun UserInfoCardComponent(
    userName: String?,
    userEmail: String?,
    userAvatarUrl: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(12.dp)
            .background(
                color = Color.Transparent
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        )
        {
            //User's Avatar
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                UserImagePickerBox(
                    userAvatarUrl = userAvatarUrl
                )
            }

            //User's Name and Email
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                //User's Name
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userName ?: "User Name",
                        style = AppTheme.typography.titleNormal,
                        color = AppTheme.colorScheme.onSecondary
                    )
                }

                //User's Email
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userEmail?: "@Email",
                        style = AppTheme.typography.labelSmall,
                        color = AppTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun UserImagePickerBox(
    userAvatarUrl: String? = null
) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(shape)
            .border(2.dp, Color.White, shape) // white border
            .clickable {
                launcher.launch("image/*")
            }
            .background(AppTheme.colorScheme.onBackground.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri.value != null) {
            ImageComponent(
                imageUrl = imageUri.value.toString(),
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
    }
}



@Preview
@Composable
fun UserInfoCardComponentPreview(){
    AppTheme(
        isDarkTheme = true
    ){
        UserInfoCardComponent(
            userName = "John Doe",
            userEmail = "JJOkocha@gmail.com"
        )
    }
}