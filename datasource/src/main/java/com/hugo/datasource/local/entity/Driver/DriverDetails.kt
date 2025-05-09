package com.hugo.datasource.local.entity.Driver

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.TableConstants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TableConstants.DRIVER_DETAILS)
data class DriverDetails(
    @PrimaryKey(autoGenerate = false)
    val driverId: String,
    val driverInfo: List<String>?= null,
    val imageUrl: String?= null,
    val firstEntry: String?= null,
    val firstWin: String?= null,
    val firstPodium: String?= null,
    val wdc: Int?= null,
    val about: String?= null,
)