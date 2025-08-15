package com.hugo.datasource.local.RoomDB

import android.content.Context
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
import dagger.hilt.android.qualifiers.ApplicationContext

class LocalDataSourceImpl(
    @ApplicationContext private val context: Context
) : LocalDataSource {

    private val appDB: AppDB = AppDB.getDatabase(context)

    override fun insertF1HomeDetailsInDB(f1HomeDetails: List<F1HomeDetails>) {
        appDB.getF1HomeDetailsDao().insertF1HomeDetailsInDB(f1HomeDetails)
    }

    override fun getF1HomeDetailsFromDB(): List<F1HomeDetails>? {
       return appDB.getF1HomeDetailsDao().getF1HomeDetailsFromDB()
    }

    override fun deleteAllF1HomeDetailsFromDB() {
        appDB.getF1HomeDetailsDao().deleteAllF1HomeDetailsFromDB()
    }

    override fun insertF1CalendarInDB(f1CalendarInfo: List<F1CalendarInfo>) {
        appDB.getF1CalendarDao().insertF1CalendarInDB(f1CalendarInfo)
    }

    override fun getF1CalendarFromDB(): List<F1CalendarInfo> {
        return appDB.getF1CalendarDao().getF1CalendarFromDB()
    }

    override fun deleteAllF1CalendarFromDB() {
        appDB.getF1CalendarDao().deleteAllF1CalendarFromDB()
    }

    override fun insertF1CircuitDetailsInDB(f1CircuitDetails: F1CircuitDetails) {
        appDB.getF1CircuitDetailsDao().insertF1CircuitDetailsInDB(f1CircuitDetails)
    }

    override fun getF1CircuitDetailsFromDB(circuitId: String): F1CircuitDetails? {
        return appDB.getF1CircuitDetailsDao().getF1CircuitDetailsFromDB(circuitId)
    }

    override fun deleteAllF1CircuitDetailsFromDB() {
        appDB.getF1CircuitDetailsDao().deleteAllF1CircuitDetailsFromDB()
    }

    override fun insertF1CalendarResultInDB(f1CalendarResult: List<F1CalendarRaceResult>) {
        appDB.getF1CalendarResultDao().insertF1CalendarResultInDB(f1CalendarResult)
    }

    override fun getF1CalendarResultFromDB(circuitId: String): List<F1CalendarRaceResult> {
        return appDB.getF1CalendarResultDao().getF1CalendarResultFromDB(circuitId)
    }

    override fun deleteAllF1CalendarResultFromDB() {
        appDB.getF1CalendarResultDao().deleteAllF1CalendarResultFromDB()
    }

    override fun insertConstructorStandingsListInDB(constructorStandingsList: List<ConstructorStandingsInfo>) {
        appDB.getConstructorStandingsDao().insertConstructorStandingsListInDB(constructorStandingsList)
    }

    override fun getConstructorStandingsListFromDB(): List<ConstructorStandingsInfo> {
        return appDB.getConstructorStandingsDao().getConstructorStandingsListFromDB()
    }

    override fun deleteAllConstructorStandingsListFromDB() {
        appDB.getConstructorStandingsDao().deleteAllConstructorStandingsListFromDB()
    }

    override fun insertConstructorQualifyingListInDB(constructorQualifyingList: List<ConstructorQualifyingResultsInfo>) {
        appDB.getConstructorQualifyingDao().insertConstructorQualifyingResultsInDB(constructorQualifyingList)
    }

    override fun getConstructorQualifyingListFromDB(constructorId: String): List<ConstructorQualifyingResultsInfo> {
        return appDB.getConstructorQualifyingDao().getConstructorQualifyingResultsListFromDB(constructorId)
    }

    override fun deleteAllConstructorQualifyingListFromDB() {
        appDB.getConstructorQualifyingDao().deleteAllConstructorQualifyingResultsListFromDB()
    }

    override fun insertConstructorRaceListInDB(constructorRaceList: List<ConstructorRaceResultsInfo>) {
        appDB.getConstructorRaceDao().insertConstructorRaceResultsListInDB(constructorRaceList)
    }

    override fun getConstructorRaceListFromDB(constructorId: String): List<ConstructorRaceResultsInfo> {
        return appDB.getConstructorRaceDao().getConstructorRaceResultsListFromDB(constructorId)
    }

    override fun deleteAllConstructorRaceListFromDB() {
        appDB.getConstructorRaceDao().deleteAllConstructorRaceResultsListFromDB()
    }

    override fun insertConstructorDetailsInDB(constructorDetails: ConstructorDetails) {
        appDB.getConstructorDetailsDao().insertConstructorDetailsInDB(constructorDetails)
    }

    override fun getConstructorDetailsFromDB(constructorId: String): ConstructorDetails? {
        return appDB.getConstructorDetailsDao().getConstructorDetailsFromDB(constructorId)
    }

    override fun deleteAllConstructorDetailsFromDB() {
        appDB.getConstructorDetailsDao().deleteAllConstructorDetailsFromDB()
    }

    override fun insertDriverStandingsListInDB(driverStandingsList: List<DriverStandingsInfo>) {
        appDB.getDriverStandingsDao().insertDriverStandingsListInDB(driverStandingsList)
    }

    override fun getDriverStandingsListFromDB(): List<DriverStandingsInfo> {
        return appDB.getDriverStandingsDao().getDriverStandingsListFromDB()
    }

    override fun deleteAllDriverStandingsListFromDB() {
        appDB.getDriverStandingsDao().deleteAllDriverStandingsListFromDB()
    }

    override fun insertDriverQualifyingListInDB(driverQualifyingList: List<DriverQualifyingResultsInfo>) {
        appDB.getDriverQualifyingDao().insertDriverQualifyingResultsInDB(driverQualifyingList)
    }

    override fun getDriverQualifyingListFromDB(driverId: String): List<DriverQualifyingResultsInfo> {
        return appDB.getDriverQualifyingDao().getDriverQualifyingResultsListFromDB(driverId)
    }

    override fun deleteAllDriverQualifyingListFromDB() {
        appDB.getDriverQualifyingDao().deleteAllDriverQualifyingResultsListFromDB()
    }

    override fun insertDriverRaceListInDB(driverRaceList: List<DriverRaceResultsInfo>) {
        appDB.getDriverRaceDao().insertDriverRaceResultsListInDB(driverRaceList)
    }

    override fun getDriverRaceListFromDB(driverId: String): List<DriverRaceResultsInfo> {
        return appDB.getDriverRaceDao().getDriverRaceResultsListFromDB(driverId)
    }

    override fun deleteAllDriverRaceListFromDB() {
        appDB.getDriverRaceDao().deleteAllDriverRaceResultsListFromDB()
    }

    override fun insertDriverDetailsInDB(driverDetails: DriverDetails) {
        appDB.getDriverDetailsDao().insertDriverDetailsInDB(driverDetails)
    }

    override fun getDriverDetailsFromDB(driverId: String): DriverDetails? {
        return appDB.getDriverDetailsDao().getDriverDetailsFromDB(driverId)
    }

    override fun deleteAllDriverDetailsFromDB() {
        appDB.getDriverDetailsDao().deleteAllDriverDetailsFromDB()
    }


}