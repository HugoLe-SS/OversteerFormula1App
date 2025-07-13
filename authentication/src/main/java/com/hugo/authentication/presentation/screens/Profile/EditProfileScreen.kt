package com.hugo.authentication.presentation.screens.Profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.design.components.AppToolbar
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    backButtonClicked: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // This LaunchedEffect will listen for a successful update and navigate back
    LaunchedEffect(state.isUpdateSuccessful) {
        if (state.isUpdateSuccessful) {
            Toast.makeText(context, "Profile Updated!", Toast.LENGTH_SHORT).show()
        }
    }

    // This can listen for errors and show a toast/snackbar
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
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
                            text = "Profile",
                            style = AppTheme.typography.titleNormal,
                        )
                    }
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Your UserImagePickerBox would go here
            Spacer(modifier = Modifier.height(32.dp))

            // TextField for the user to edit their name
            OutlinedTextField(
                value = state.displayName,
                onValueChange = { newName -> viewModel.onDisplayNameChange(newName) },
                label = { Text("Display Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // The Save button
            Button(
                onClick = { viewModel.onSaveChangesClicked() },
                enabled = !state.isLoading, // Disable button while loading
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Save Changes")
                }
            }
        }
    }
}