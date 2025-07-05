package com.hugo.authentication.presentation.screens.Auth

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.authentication.R
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    backButtonClicked: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            // Show snackbar or toast
            Log.e("GoogleSignIn", error)
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppToolbar(
                    navigationIcon = {
                        IconButton(
                            onClick = { backButtonClicked() }
                        ) {
                            ImageComponent(
                                imageResourceValue = com.hugo.design.R.drawable.ic_back,
                                contentDescription = "Back Button",
                            )
                        }
                    },

                    title = {
                        Text(
                            text = context.getString(R.string.my_account),
                            style = AppTheme.typography.titleNormal,
                        )
                    }
                )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(innerPadding)
        ){
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ImageComponent(
                    //imageResourceValue = R.drawable.f1banner,
                    //imageResourceValue = R.drawable.formula1,
                )

                if (state.isSignedIn && state.userInfo != null) {
                    // Signed in UI
                    EditProfileContent(
                    uiState = state,
                    onDisplayNameChange = viewModel::onDisplayNameChange,
                    onNewAvatarSelected = viewModel::onAvatarUriSelected,
                    onSaveChanges = viewModel::saveChanges,
                    onSignOut = viewModel::signOut,
                    )
                } else {
                    // Sign in UI
                    SignInContent(
                        isLoading = state.isLoading,
                        errorMessage = state.errorMessage,
                        onSignIn = { viewModel.signInWithGoogle() },
                        onClearError = { viewModel.clearError() }
                    )
                }

            }
        }

    }
}

@Composable
private fun SignInContent(
    isLoading: Boolean,
    errorMessage: String?,
    onSignIn: () -> Unit,
    onClearError: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onSignIn,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(48.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = AppTheme.colorScheme.onSecondary
                )
            } else {
                Text("Sign in with Google")
            }
        }

        errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Error: $error",
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.primary
            )
            TextButton(onClick = onClearError) {
                Text("Dismiss")
            }
        }
    }
}

@Composable
private fun EditProfileContent(
    uiState: AuthUiState,
    onDisplayNameChange: (String) -> Unit,
    onNewAvatarSelected: (Uri?) -> Unit,
    onSaveChanges: () -> Unit,
    onSignOut: () -> Unit,
) {
    // Example layout:
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri -> onNewAvatarSelected(uri) }
        )

        // Pass the URI for preview from the state
        UserImagePickerBox(
            imageUri = uiState.newAvatarUri,
            userAvatarUrl = uiState.userInfo?.profilePictureUrl,
            onBoxClicked = { launcher.launch("image/*") }
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.editableDisplayName,
            onValueChange = onDisplayNameChange,
            label = { Text("Display Name") }
        )

        Spacer(Modifier.height(24.dp))

        Button(onClick = onSaveChanges, enabled = !uiState.isLoading) {
            Text("Save Changes")
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onSignOut) {
            Text("Sign Out")
        }
    }
}

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

@Preview
@Composable
fun LoginScreenPreview(){
    AppTheme(
        isDarkTheme = true
    ){
        AuthScreen(
        )
    }

}