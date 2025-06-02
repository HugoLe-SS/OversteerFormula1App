package com.hugo.design.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hugo.design.R
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.AppError
import com.hugo.utilities.RemoteErrorWithCode

@Composable
fun ErrorDisplayComponent(
    appError: AppError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var title = "Oops!"
    var message = "An unexpected error occurred. Please try again."
    var icon: Int = R.drawable.ic_error

    when (appError) {
        is AppError.RemoteError -> {
            when (appError) {
                AppError.RemoteError.NO_INTERNET_ERROR -> {
                    title = "No Internet Connection"
                    message = "Please check your network settings and try again."
                    icon = R.drawable.ic_cloudoff
                }
                AppError.RemoteError.SERVER_ERROR -> {
                    title = "Server Error"
                    message = "We're having trouble connecting to our servers. Please try again later."
                    icon = R.drawable.ic_error
                }
                AppError.RemoteError.REQUEST_TIMEOUT_ERROR -> {
                    title = "Request Timed Out"
                    message = "The request took too long to respond. Please check your connection or try again."
                    icon = R.drawable.ic_error
                }
                AppError.RemoteError.TOO_MANY_REQUESTS_ERROR -> {
                    title = "Too Many Requests"
                    message = "You've made too many requests. Please wait a moment and try again."
                    icon = R.drawable.ic_error
                }
                AppError.RemoteError.CLIENT_ERROR -> { // Generic client error
                    title = "Request Issue"
                    message = "There was an issue with your request."
                    icon = R.drawable.ic_error
                }
                AppError.RemoteError.SERIALIZATION_ERROR -> {
                    title = "Data Error"
                    message = "Could not understand the data from the server."
                    icon = R.drawable.ic_error
                }
                AppError.RemoteError.UNKNOWN_ERROR -> {
                    title = "Network Issue"
                    message = "An unknown network error occurred."
                    icon = R.drawable.ic_error
                }
                else -> {
                    title = "Network Problem"
                    message = "A network problem occurred."
                    icon = R.drawable.ic_error
                }
            }
        }

        is RemoteErrorWithCode -> {
            title = "Error"
            message = appError.specificMessage ?: "An error occurred (Code: ${appError.code})."
            icon = R.drawable.ic_error

            when (appError.error) {
                AppError.RemoteError.CLIENT_ERROR -> {
                    title = "Request Error"
                    if (appError.code == 404) {
                        message = "The requested information could not be found (Error 404)."
                    } else if (appError.code == 401 || appError.code == 403) {
                        message = "You are not authorized to access this (Error ${appError.code})."
                    }
                }
                AppError.RemoteError.SERVER_ERROR -> title = "Server Issue"
                AppError.RemoteError.TOO_MANY_REQUESTS_ERROR -> title = "Too Many Requests"
                else -> {}
            }
        }

        is AppError.LocalError -> {
            when (appError) {
                AppError.LocalError.DATABASE_ERROR -> {
                    title = "Local Data Error"
                    message = "There was a problem accessing local data. Please try restarting the app."
                }
                AppError.LocalError.CACHE_ERROR -> {
                    title = "Cache Error"
                    message = "There was an issue with cached data."
                }
                else -> {
                    title = "Local Storage Problem"
                    message = "An issue occurred with local storage."
                }
            }
        }

        else -> {
            title = "Something Went Wrong"
            message = "An unknown error occurred. Please try again."
            icon = R.drawable.ic_error
        }
    }


    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageComponent(
                 imageResourceValue = icon,
                 contentDescription = "Error Icon",
                 modifier = Modifier.size(48.dp)
            )

            Text(
                text = title,
                style = AppTheme.typography.titleLarge,
                color = AppTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = AppTheme.typography.labelNormal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

