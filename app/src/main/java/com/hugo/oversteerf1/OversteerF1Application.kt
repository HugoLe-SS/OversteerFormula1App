package com.hugo.oversteerf1

import android.app.Application
import com.hugo.utilities.logging.AppLogger
import dagger.hilt.android.HiltAndroidApp
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

@HiltAndroidApp
class OversteerF1Application : Application() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    private val applicationScope = CoroutineScope(SupervisorJob())


    override fun onCreate() {
        super.onCreate()
        AppLogger.d(message = "Application is launched")
    }

    companion object {
        const val TAG = "OversteerF1Application"
    }

}