package com.hugo.utilities.com.hugo.utilities.Navigation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json


//object CalendarClickInfoNavType : NavType<CalendarClickInfo>(isNullableAllowed = false) {
//    override fun get(bundle: Bundle, key: String): CalendarClickInfo? {
//        return bundle.getString(key)?.let { Json.decodeFromString(it) }
//    }
//
//    override fun parseValue(value: String): CalendarClickInfo {
//        return Json.decodeFromString(value)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: CalendarClickInfo) {
//        bundle.putString(key, Json.encodeToString(value))
//    }
//}

object CustomNavType{

    val CalendarClickInfoNavType = object : NavType<CalendarClickInfo>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): CalendarClickInfo? {
            return bundle.getString(key)?.let { Json.decodeFromString(it) }
        }

        override fun parseValue(value: String): CalendarClickInfo {
            return Json.decodeFromString(value)
        }

        override fun serializeAsValue(value: CalendarClickInfo): String {
            return Json.encodeToString(value)
        }

        override fun put(bundle: Bundle, key: String, value: CalendarClickInfo) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

}