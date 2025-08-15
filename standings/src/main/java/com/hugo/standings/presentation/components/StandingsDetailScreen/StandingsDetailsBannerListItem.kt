package com.hugo.standings.presentation.components.StandingsDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.design.components.StandingsDetailsBannerComponent
import com.hugo.design.ui.theme.AppColors
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo

@Composable
fun StandingsDetailsBannerListItem(
    modifier: Modifier = Modifier, // Added modifier to apply to the root
    driverDetails: DriverDetails? = null,
    constructorDetails: ConstructorDetails? = null,
    driverClickInfo: DriverClickInfo? = null, // Primarily for ID and initial data
    constructorClickInfo: ConstructorClickInfo? = null, // Primarily for ID and initial data
    viewResultButtonClicked: () -> Unit = {}
) {
    // Determine mode based on which primary info is available
    val isDriverMode = driverClickInfo?.driverId != null || driverDetails?.driverId != null
    val isConstructorMode = constructorClickInfo?.constructorId != null || constructorDetails?.constructorId != null

    // Determine team color
    val teamIdForColor = if (isDriverMode) {
        driverClickInfo?.constructorId
    } else if (isConstructorMode) {
        constructorClickInfo?.constructorId ?: constructorDetails?.constructorId
    } else {
        null
    }
    val teamColor = AppColors.Teams.colors[teamIdForColor] ?: AppTheme.colorScheme.onSecondary

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            AppTheme.colorScheme.background,
            teamColor.copy(alpha = 0.6f),
            AppTheme.colorScheme.background,
        )
    )

    // Data extraction with clarity
    val name: String
    val description: String // e.g., familyName or chassis
    val season: String
    val position: String
    val points: String
    val wins: String
    val imageUrl: String

    // Stats - ASSUMING SAFER ACCESS or structured data
    // For demonstration, using getOrNull with a default. Replace with structured access.
    val podiums: String
    val poles: String
    val dnf: String

    if (isDriverMode) {
        name = driverClickInfo?.givenName ?: ""
        description = driverClickInfo?.familyName ?: ""
        season = driverClickInfo?.season ?:  ""
        position = driverClickInfo?.position ?: ""
        points = driverClickInfo?.points ?:  ""
        wins = driverClickInfo?.wins ?: ""
        imageUrl = driverDetails?.imageUrl ?: ""

        // SAFER STATS ACCESS (replace with your actual data structure)
        podiums = driverDetails?.driverStats?.getOrNull(0) ?: ""
        poles = driverDetails?.driverStats?.getOrNull(1) ?: ""
        dnf = driverDetails?.driverStats?.getOrNull(2) ?: ""
    } else if (isConstructorMode) {
        name = constructorClickInfo?.constructorName ?:  ""
        description = constructorDetails?.chassis ?: ""
        season = constructorClickInfo?.season ?:  ""
        position = constructorClickInfo?.position ?:  ""
        points = constructorClickInfo?.points ?:  ""
        wins = constructorClickInfo?.wins ?:  ""
        imageUrl = constructorDetails?.imageUrl ?: ""

        podiums = constructorDetails?.constructorStats?.getOrNull(0) ?: ""
        poles = constructorDetails?.constructorStats?.getOrNull(1) ?: ""
        dnf = constructorDetails?.constructorStats?.getOrNull(2) ?: ""
    } else {
        // Fallback if neither mode is determined (should ideally not happen if called correctly)
        name = "N/A"; description = "N/A"; season = ""; position = ""; points = ""; wins = "";
        imageUrl = ""; podiums = ""; poles = ""; dnf = "";
    }

    Box(
        modifier = modifier // Apply the passed modifier
            .background(brush = gradientBrush)
    ) {
        StandingsDetailsBannerComponent(
            name = name,
            description = description,
            season = season,
            position = position,
            points = points,
            wins = wins,
            podiums = podiums,
            poles = poles,
            dnf = dnf,
            teamColor = teamColor,
            driverImgUrl = imageUrl, // Parameter name in inner component might need to be more generic like 'entityImgUrl'
            buttonClicked = {
                if (isDriverMode) {
                    viewResultButtonClicked()
                } else if (isConstructorMode) {
                    viewResultButtonClicked()
                }
            }
        )
    }
}



