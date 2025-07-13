package com.hugo.datasource.local.entity.Constructor

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.RoomDB.TableConstants
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TableConstants.CONSTRUCTOR_DETAILS)
data class ConstructorDetails(
    @PrimaryKey(autoGenerate = false)
    val constructorId: String,
    val firstDriver: List<String>?= null,
    val secondDriver: List<String>?= null,
    val constructorStats: List<String>?= null,
    val imageUrl: String?= null,
    val chassis: String?= null,
    val powerUnit: String?= null,
    val teamPrincipal: String?= null,
    val firstEntry: String?= null,
    val wcc: Int?= null,
    val wdc: Int?= null,
    val about:String?= null,
)
