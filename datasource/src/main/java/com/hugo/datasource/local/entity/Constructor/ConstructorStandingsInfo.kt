package com.hugo.datasource.local.entity.Constructor

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.datasource.local.TableConstants

@Entity(tableName = TableConstants.CONSTRUCTOR_STANDINGS_LIST)
data class ConstructorStandingsInfo(
    @PrimaryKey(autoGenerate = false)
    val constructorId: String,
    val total: Int,
    val season: String,
    val round: String,
    val position: String,
    val points: String,
    val wins: String,
    val constructorName: String,
    val constructorNationality: String
)
