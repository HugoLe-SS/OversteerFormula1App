package com.hugo.authentication.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugo.authentication.R
import com.hugo.authentication.domain.model.GoogleSignInResult
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme

@Composable
fun AuthScreen(
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
            .padding(12.dp)
    ){
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.TopEnd
            ){
              Text(
                  text = stringResource(R.string.skip),
                  style = AppTheme.typography.labelNormal,
                  color = AppTheme.colorScheme.onSecondary
              )
            }

            ImageComponent(
                //imageResourceValue = R.drawable.f1banner,
                //imageResourceValue = R.drawable.formula1,
            )

            if (state.isSignedIn && state.userInfo != null) {
                // Signed in UI
                SignedInContent(
                    userInfo = state.userInfo!!,
                    onSignOut = { viewModel.signOut(context) }
                )
            } else {
                // Sign in UI
                SignInContent(
                    isLoading = state.isLoading,
                    errorMessage = state.errorMessage,
                    onSignIn = { viewModel.signInWithGoogle(context) },
                    onClearError = { viewModel.clearError() }
                )
            }

        }
    }
}

@Composable
fun GoogleSignInButton(
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(48.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = AppTheme.colorScheme.onPrimary
                )
            } else {
                Text("Sign in with Google")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "By signing up, you agree to our Terms of Service",
            style = AppTheme.typography.labelMini,
            color = AppTheme.colorScheme.onSecondary,
        )
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
private fun SignedInContent(
    userInfo: GoogleSignInResult,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome, ${userInfo.displayName ?: "User"}!",
            style = AppTheme.typography.titleNormal,
            color = AppTheme.colorScheme.onSecondary
        )
        Text(
            text = "Email: ${userInfo.email}",
            style = AppTheme.typography.body,
            color = AppTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSignOut) {
            Text("Sign Out")
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