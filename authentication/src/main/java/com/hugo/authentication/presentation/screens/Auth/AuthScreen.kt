package com.hugo.authentication.presentation.screens.Auth

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.authentication.R
import com.hugo.authentication.presentation.components.UserImagePickerBox
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.ConfirmationDialog
import com.hugo.design.components.ImageComponent
import com.hugo.design.components.LoadingIndicatorComponent
import com.hugo.design.components.StyledOutlinedButton
import com.hugo.design.components.StyledOutlinedTextField
import com.hugo.design.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    backButtonClicked: () -> Unit = {},
    skipButtonClicked: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel(),
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
                if (state.isSignedIn && state.userInfo != null){
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
                else{
                    AppToolbar(
                        actions = {
                            TextButton(
                                onClick = { skipButtonClicked() }
                            ) {
                                Text(
                                    text = context.getString(R.string.skip),
                                    style = AppTheme.typography.labelNormal,
                                    color = AppTheme.colorScheme.onSecondary
                                )
                            }
                        },

                    )
                }

            }
        },
    ) { innerPadding ->
        when {
            state.isInitialLoading ->   Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicatorComponent(
                    paddingValues = innerPadding,
                )
            }
            else ->
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

                        if (state.isSignedIn && state.userInfo != null) {
                            // Signed in UI
                            EditProfileComposable(
                                uiState = state,
                                onDisplayNameChange = viewModel::onDisplayNameChange,
                                onNewAvatarSelected = viewModel::onAvatarUriSelected,
                                onSaveChanges = viewModel::saveChanges,
                                onSignOut = viewModel::signOut,
                                onDelete = viewModel::deleteAccount
                            )
                        } else {
                            // Sign in UI
                            GoogleSignInComposable(
                                isLoading = state.isLoading,
                                errorMessage = state.errorMessage,
                                onSignIn = { viewModel.signInWithGoogle() },
                                onClearError = { viewModel.clearError() },
                            )
                        }

                    }
                }
        }

    }
}

@Composable
private fun GoogleSignInComposable(
    isLoading: Boolean,
    errorMessage: String?,
    onSignIn: () -> Unit,
    onClearError: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        //F1 Banner
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            ImageComponent(
                imageResourceValue = R.drawable.f1_banner
            )
        }

        StyledOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            text = stringResource(R.string.sign_in_with_google),
            isLoading = isLoading,
            onClick = onSignIn,
            icon = painterResource(id = R.drawable.ic_google_icon)
        )

//        errorMessage?.let { error ->
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Error: $error",
//                style = AppTheme.typography.labelSmall,
//                color = AppTheme.colorScheme.primary
//            )
//            TextButton(onClick = onClearError) {
//                Text("Dismiss")
//            }
//        }
    }
}

@Composable
private fun EditProfileComposable(
    uiState: AuthUiState,
    onDisplayNameChange: (String) -> Unit,
    onNewAvatarSelected: (Uri?) -> Unit,
    onSaveChanges: () -> Unit,
    onSignOut: () -> Unit,
    onDelete: () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        var showSaveChangesDialog by remember { mutableStateOf(false) }
        var showSignOutDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri -> onNewAvatarSelected(uri) }
        )

        // Dialog for Saving Changes
        ConfirmationDialog(
            show = showSaveChangesDialog,
            title = "Save Changes",
            text = "Are you sure you want to save these changes?",
            confirmButtonText = "Save",
            onConfirm = {
                showSaveChangesDialog = false
                onSaveChanges()
            },
            onDismiss = { showSaveChangesDialog = false }
        )

        // Dialog for Signing Out
        ConfirmationDialog(
            show = showSignOutDialog,
            title = "Sign Out",
            text = "Are you sure you want to sign out?",
            confirmButtonText = "Sign Out",
            onConfirm = {
                showSignOutDialog = false
                onSignOut()
            },
            onDismiss = { showSignOutDialog = false }
        )

        // Dialog for Deleting Account
        ConfirmationDialog(
            show = showDeleteDialog,
            title = "Delete Account",
            text = "This action is permanent and cannot be undone. Are you absolutely sure?",
            confirmButtonText = "Delete Permanently",
            onConfirm = {
                showDeleteDialog = false
                onDelete()
            },
            onDismiss = { showDeleteDialog = false }
        )

        UserImagePickerBox(
            imageUri = uiState.newAvatarUri,
            userAvatarUrl = uiState.userInfo?.profilePictureUrl,
            onBoxClicked = { launcher.launch("image/*") }
        )

        Spacer(Modifier.height(24.dp))

        StyledOutlinedTextField(
            value = uiState.editableDisplayName,
            onValueChange = onDisplayNameChange,
            label = stringResource(R.string.display_name),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        //Save Changes Button
        StyledOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            text = stringResource(R.string.save_changes),
            isLoading = false,
            onClick = {showSaveChangesDialog = true},
        )

        Spacer(Modifier.height(16.dp))


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //Sign Out Button
            StyledOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                text = stringResource(R.string.sign_out),
                onClick = {showSignOutDialog = true},
            )

            Spacer(Modifier.height(32.dp))

            StyledOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                containerColor = Color.Red,
                text = stringResource(R.string.delete_account),
                onClick = {showDeleteDialog = true},
                icon = painterResource(id = R.drawable.ic_delete),
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