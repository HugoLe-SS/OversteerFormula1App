package com.hugo.utilities.logging

import android.util.Log
import com.hugo.utilities.BuildConfig

object AppLogger {

    private const val TAG = "AppLogger"

    var isTesting: Boolean = false

    fun d(tag: String = TAG, message: String) {
        if (isTesting) return // skip logging in local tests

        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun e(tag: String = TAG, message: String) {
        if (isTesting) return // skip logging in local tests

        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }
}