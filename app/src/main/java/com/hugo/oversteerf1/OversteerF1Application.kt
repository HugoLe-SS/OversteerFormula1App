package com.hugo.oversteerf1

import android.app.Application
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OversteerF1Application : Application() {
    override fun onCreate() {
        super.onCreate()
        AppLogger.d(message = "Application is launched")
    }

    companion object {
        const val TAG = "OversteerF1Application"
    }
}