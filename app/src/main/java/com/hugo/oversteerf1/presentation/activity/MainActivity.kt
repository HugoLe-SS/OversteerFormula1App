package com.hugo.oversteerf1.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hugo.design.ui.theme.AppTheme
import com.hugo.oversteerf1.presentation.screens.navigation.AppNavGraph
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity () : ComponentActivity() {

    @Inject
    lateinit var supabaseClient: SupabaseClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

        askNotificationPermission()

        setContent {
            AppTheme {
                AppNavGraph()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            AppLogger.d(message = "Notification permission GRANTED")
            //storeFcmToken(permissionGranted = true)
        } else {
            AppLogger.d(message = "Notification permission DENIED")
            //storeFcmToken(permissionGranted = false)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    AppLogger.d(message = "Notification permission already granted")
                    return
                }
                else -> {
                    // Request permission directly with system dialog
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            AppLogger.d(message = "Notification permission not required for this Android version")
            //storeFcmToken(permissionGranted = true)
        }
    }


}



