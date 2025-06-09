package com.hugo.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hugo.datasource.dao.Constructor.ConstructorDetailsDao
import com.hugo.datasource.dao.Constructor.ConstructorQualifyingResultsDao
import com.hugo.datasource.dao.Constructor.ConstructorRaceResultsDao
import com.hugo.datasource.dao.Constructor.ConstructorStandingsDao
import com.hugo.datasource.dao.Driver.DriverDetailsDao
import com.hugo.datasource.dao.Driver.DriverQualifyingResultsDao
import com.hugo.datasource.dao.Driver.DriverRaceResultsDao
import com.hugo.datasource.dao.Driver.DriverStandingsDao
import com.hugo.datasource.dao.Home.F1HomeDetailsDao
import com.hugo.datasource.dao.Schedule.F1CalendarDao
import com.hugo.datasource.dao.Schedule.F1CalendarResult
import com.hugo.datasource.dao.Schedule.F1CircuitDetailsDao
import com.hugo.datasource.local.converter.Converters
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Constructor.ConstructorQualifyingResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Constructor.ConstructorStandingsInfo
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.datasource.local.entity.Driver.DriverQualifyingResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverStandingsInfo
import com.hugo.datasource.local.entity.F1HomeDetails
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails

@Database(entities =

[
    F1HomeDetails::class ,F1CalendarInfo::class, F1CircuitDetails::class, F1CalendarRaceResult::class,
    ConstructorStandingsInfo::class, ConstructorDetails::class, ConstructorQualifyingResultsInfo::class,
    ConstructorRaceResultsInfo:: class, DriverStandingsInfo::class, DriverDetails::class,
    DriverRaceResultsInfo::class, DriverQualifyingResultsInfo::class
],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {

    abstract fun getF1HomeDetailsDao(): F1HomeDetailsDao

    abstract fun getF1CalendarDao(): F1CalendarDao

    abstract fun getF1CircuitDetailsDao(): F1CircuitDetailsDao

    abstract fun getF1CalendarResultDao(): F1CalendarResult

    abstract fun getConstructorStandingsDao(): ConstructorStandingsDao

    abstract fun getConstructorQualifyingDao(): ConstructorQualifyingResultsDao

    abstract fun getConstructorRaceDao(): ConstructorRaceResultsDao

    abstract fun getConstructorDetailsDao(): ConstructorDetailsDao

    abstract fun getDriverStandingsDao(): DriverStandingsDao

    abstract fun getDriverQualifyingDao(): DriverQualifyingResultsDao

    abstract fun getDriverRaceDao(): DriverRaceResultsDao

    abstract fun getDriverDetailsDao(): DriverDetailsDao


    companion object{

        @Volatile
        private var instance: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AppDB {
            return Room.databaseBuilder(context, AppDB::class.java, TableConstants.APP_DB_NAME)
                .fallbackToDestructiveMigrationFrom(true)
                .allowMainThreadQueries()
                .build()
        }

        fun destroyDataBase(){
            instance = null
        }

    }
}


object TableConstants {
    const val APP_DB_NAME = "OVERSTEER_Database"

    const val F1_HOME_DETAILS = "F1_Home_Details"

    const val F1_CALENDAR_INFO_LIST = "F1_Calendar_Info_List"
    const val F1_CIRCUIT_DETAILS = "F1_Circuit_Details"
    const val F1_CALENDAR_RESULT_LIST = "F1_Calendar_Result_List"

    const val CONSTRUCTOR_STANDINGS_LIST = "Constructor_Standings_List"
    const val CONSTRUCTOR_QUALIFYING_LIST = "Constructor_Qualifying_List"
    const val CONSTRUCTOR_RACE_LIST = "Constructor_Race_List"
    const val CONSTRUCTOR_DETAILS = "Constructor_Details"

    const val DRIVER_STANDINGS_LIST = "Driver_Standings_List"
    const val DRIVER_QUALIFYING_LIST = "Driver_Qualifying_List"
    const val DRIVER_RACE_LIST = "Driver_Race_List"
    const val DRIVER_DETAILS = "Driver_Details"
}