package com.hugo.datasource.local

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

interface LocalDataSource {

    //F1 Home
    fun insertF1HomeDetailsInDB(f1CalendarInfo: F1HomeDetails)
    fun getF1HomeDetailsFromDB(): F1HomeDetails?
    fun deleteAllF1HomeDetailsFromDB()

    //F1 Calendar
    fun insertF1CalendarInDB(f1CalendarInfo: List<F1CalendarInfo>)
    fun getF1CalendarFromDB(): List<F1CalendarInfo>
    fun deleteAllF1CalendarFromDB()

    // F1 Circuit Details
    fun insertF1CircuitDetailsInDB(f1CircuitDetails: F1CircuitDetails)
    fun getF1CircuitDetailsFromDB(circuitId: String): F1CircuitDetails?
    fun deleteAllF1CircuitDetailsFromDB()

    //F1 Calendar Result
    fun insertF1CalendarResultInDB(f1CalendarResult: List<F1CalendarRaceResult>)
    fun getF1CalendarResultFromDB(circuitId: String): List<F1CalendarRaceResult>
    fun deleteAllF1CalendarResultFromDB()


    //Constructor Standings
    fun insertConstructorStandingsListInDB(constructorStandingsList: List<ConstructorStandingsInfo>)
    fun getConstructorStandingsListFromDB(): List<ConstructorStandingsInfo>
    fun deleteAllConstructorStandingsListFromDB()

    //Constructor Qualifying
    fun insertConstructorQualifyingListInDB(constructorQualifyingList: List<ConstructorQualifyingResultsInfo>)
    fun getConstructorQualifyingListFromDB(constructorId: String): List<ConstructorQualifyingResultsInfo>
    fun deleteAllConstructorQualifyingListFromDB()

    // Constructor Race
    fun insertConstructorRaceListInDB(constructorRaceList: List<ConstructorRaceResultsInfo>)
    fun getConstructorRaceListFromDB(constructorId: String): List<ConstructorRaceResultsInfo>
    fun deleteAllConstructorRaceListFromDB()

    // Constructor Details
    fun insertConstructorDetailsInDB(constructorDetails: ConstructorDetails)
    fun getConstructorDetailsFromDB(constructorId: String): ConstructorDetails?
    fun deleteAllConstructorDetailsFromDB()


    //Driver Standings
    fun insertDriverStandingsListInDB(driverStandingsList: List<DriverStandingsInfo>)
    fun getDriverStandingsListFromDB(): List<DriverStandingsInfo>
    fun deleteAllDriverStandingsListFromDB()

    //Driver Qualifying
    fun insertDriverQualifyingListInDB(driverQualifyingList: List<DriverQualifyingResultsInfo>)
    fun getDriverQualifyingListFromDB(driverId: String): List<DriverQualifyingResultsInfo>
    fun deleteAllDriverQualifyingListFromDB()

    //Driver Race
    fun insertDriverRaceListInDB(driverRaceList: List<DriverRaceResultsInfo>)
    fun getDriverRaceListFromDB(driverId: String): List<DriverRaceResultsInfo>
    fun deleteAllDriverRaceListFromDB()

    //Driver Details
    fun insertDriverDetailsInDB(driverDetails: DriverDetails)
    fun getDriverDetailsFromDB(driverId: String): DriverDetails?
    fun deleteAllDriverDetailsFromDB()

}