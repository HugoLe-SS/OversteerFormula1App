package com.hugo.result.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Constructor.ConstructorRaceResultsInfo
import com.hugo.datasource.local.entity.Driver.DriverRaceResultsInfo
import com.hugo.datasource.local.entity.Schedule.F1CalendarRaceResult
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.AppUtilities.formatMillisToTime
import com.hugo.utilities.AppUtilities.toShortGPFormat

@Composable
fun RaceResultListItem(
    driverRaceResult: DriverRaceResultsInfo? = null,
    //driverQuali: DriverQualifyingResultsInfo?= null,
    constructorRaceResult: ConstructorRaceResultsInfo?= null,
    //constructorQuali: ConstructorQualifyingResultsInfo?= null,
    circuitRaceResult: F1CalendarRaceResult?= null,
    interval: String? = null // Interval is the gap between the driver and the driver ahead of them
){
    val teamColor = AppColors.Teams.colors[circuitRaceResult?.constructorId]
        ?: AppTheme.colorScheme.onSecondary

    val circuitColor = AppColors.Circuit.colors[AppColors.Circuit.circuitContinentMap[constructorRaceResult?.circuitId?: driverRaceResult?.circuitId] ?: ""]
        ?: AppTheme.colorScheme.onSecondary


    driverRaceResult?.let{
            ResultCardComponent(
                position = driverRaceResult.position,
                raceName = driverRaceResult.raceName.toShortGPFormat(),
                circuitNation = driverRaceResult.country,
                points = driverRaceResult.points,
                grid = driverRaceResult.grid,
                teamColor = circuitColor,
//                interval = "_"  , //
                gapToLeader = driverRaceResult.time, // If you are the Leader then it will displays the Time it takes to finished the race
                fastestLap = driverRaceResult.fastestLap,
                status = driverRaceResult.status

            )
        }

    constructorRaceResult?.let{
            ResultCardComponent(
                position = constructorRaceResult.position,
                driverName = constructorRaceResult.givenName + " " + constructorRaceResult.familyName,
                raceName = constructorRaceResult.raceName.toShortGPFormat(),
                points = constructorRaceResult.points,
                grid = constructorRaceResult.grid,
                teamColor = circuitColor,
                interval = interval , //
                gapToLeader = constructorRaceResult.time, // If you are the Leader then it will displays the Time it takes to finished the race
                fastestLap = constructorRaceResult.fastestLap,
                status = constructorRaceResult.status
            )
        }

    circuitRaceResult?.let {
            ResultCardComponent(
                position = circuitRaceResult.position,
                driverName = circuitRaceResult.givenName + " " + circuitRaceResult.familyName,
                points = circuitRaceResult.points,
                grid = circuitRaceResult.grid,
                constructorName = circuitRaceResult.constructorName,
                teamColor = teamColor,
                interval = interval , // Interval is the gap between the driver and the driver ahead of them
                gapToLeader = circuitRaceResult.time, // If you are the Leader then it will displays the Time it takes to finished the race
                fastestLap = circuitRaceResult.fastestLap,
                status = circuitRaceResult.status

            )
        }

    Spacer(
        Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(AppTheme.colorScheme.onBackground)
    )


}



fun calculateIntervals(results: List<F1CalendarRaceResult>): List<String?> {
    if (results.isEmpty()) {
        return emptyList()
    }

    val leader = results.first()
    val leaderLaps = leader.laps.toIntOrNull()

    return results.mapIndexed { index, current ->
        val currentStatus = current.status.lowercase()

        val statusAbbreviation = when {
            currentStatus.contains("did not start") || currentStatus == "dns" -> "DNS"
            currentStatus.contains("retired") || currentStatus == "dnf" -> "DNF"
            currentStatus.contains("disqualified") || currentStatus == "dsq" -> "DSQ"
            currentStatus.contains("did not qualify") || currentStatus == "dnq" -> "DNQ"
            else -> null
        }
        if (statusAbbreviation != null) {
            return@mapIndexed statusAbbreviation
        }

        if (index == 0) {
            // Leader has no interval to a car ahead.
            return@mapIndexed null
        }

        //Lapped Drivers
        val currentDriverLaps = current.laps.toIntOrNull()
        if (leaderLaps != null && currentDriverLaps != null && currentDriverLaps < leaderLaps) {
            val lapsDown = leaderLaps - currentDriverLaps
            return@mapIndexed "+$lapsDown Lap${if (lapsDown > 1) "s" else ""}"
        }

         if (currentStatus == "lapped" && (leaderLaps == null || currentDriverLaps == null)) {
            return@mapIndexed "Lapped"
         }

        //    calculate interval
        val previousResult = results[index - 1]
        val currentDriverMillis = current.millis?.toLongOrNull()
        val previousDriverMillis = previousResult.millis?.toLongOrNull()

        if (currentDriverMillis != null && previousDriverMillis != null) {
            val previousDriverLaps = previousResult.laps.toIntOrNull()
            if (leaderLaps != null && currentDriverLaps != null && previousDriverLaps != null) {
                if (currentDriverLaps < previousDriverLaps) {
                    return@mapIndexed current.time
                }
            }

            val intervalMillis = currentDriverMillis - previousDriverMillis
            if (intervalMillis >= 0) {
                return@mapIndexed intervalMillis.formatMillisToTime()
            } else {
                return@mapIndexed current.time
            }
        } else {
            return@mapIndexed current.time
        }
    }
}
