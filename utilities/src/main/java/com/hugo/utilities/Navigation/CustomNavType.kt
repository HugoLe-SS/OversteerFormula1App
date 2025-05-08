package com.hugo.utilities.com.hugo.utilities.Navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.hugo.utilities.com.hugo.utilities.Navigation.model.CalendarClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo
import kotlinx.serialization.json.Json

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

    val ConstructorClickInfoNavType = object : NavType<ConstructorClickInfo?>(isNullableAllowed = true) {
        override fun get(bundle: Bundle, key: String): ConstructorClickInfo? {
            return bundle.getString(key)?.let { Json.decodeFromString(it) }
        }

        override fun parseValue(value: String): ConstructorClickInfo? {
            return if (value == "null") null else Json.decodeFromString(value)
        }

        override fun serializeAsValue(value: ConstructorClickInfo?): String {
            return Json.encodeToString(value)
        }

        override fun put(bundle: Bundle, key: String, value: ConstructorClickInfo?) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

    val DriverClickInfoNavType = object : NavType<DriverClickInfo?>(isNullableAllowed = true) {
        override fun get(bundle: Bundle, key: String): DriverClickInfo? {
            return bundle.getString(key)?.let { Json.decodeFromString(it) }
        }

        override fun parseValue(value: String): DriverClickInfo? {
            return if (value == "null") null else Json.decodeFromString(value)
        }

        override fun serializeAsValue(value: DriverClickInfo?): String {
            return Json.encodeToString(value)
        }

        override fun put(bundle: Bundle, key: String, value: DriverClickInfo?) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

}