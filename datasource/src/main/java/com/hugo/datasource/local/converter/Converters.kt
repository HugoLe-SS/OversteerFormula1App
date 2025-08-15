package com.hugo.datasource.local.converter

import androidx.room.TypeConverter
import com.hugo.datasource.local.entity.Schedule.SessionInfo
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.split(",")
    }

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromSessionInfo(sessionInfo: SessionInfo?): String? {
        return sessionInfo?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toSessionInfo(jsonString: String?): SessionInfo? {
        return jsonString?.let {
            try {
                json.decodeFromString<SessionInfo>(it)
            } catch (e: SerializationException) {
                System.err.println("Error decoding SessionInfo from JSON: $it. Error: ${e.message}")
                null
            } catch (e: Exception) {
                System.err.println("Unexpected error decoding SessionInfo: $it. Error: ${e.message}")
                null
            }
        }
    }
}

