package com.hugo.standings.presentation.components.StandingsDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hugo.datasource.local.entity.Constructor.ConstructorDetails
import com.hugo.datasource.local.entity.Driver.DriverDetails
import com.hugo.design.R.drawable
import com.hugo.design.components.ImageComponent
import com.hugo.design.ui.theme.AppTheme
import com.hugo.design.utilities.Flag
import com.hugo.standings.R

@Composable fun DriverBioList(
    driverDetails: DriverDetails,
){

    val driverNationality = remember(driverDetails.driverInfo?.getOrNull(3)) {
        Flag.getFlagImageRes(country = driverDetails.driverInfo?.getOrNull(3) ?: "")
    }


    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ){
        StandingsBioItem(
            imageResourceValue = drawable.ic_driver,
            info = driverDetails.driverInfo?.getOrNull(0) ?: "",
            infoTag = stringResource(R.string.driver_code)
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_engine,
            info = driverDetails.driverInfo?.getOrNull(1) ?: "",
            infoTag = stringResource(R.string.team)
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_calendar,
            info = "${driverDetails.firstEntry}",
            infoTag = stringResource(R.string.first_entry)
        )

        driverDetails.firstWin?.let {
            StandingsBioItem(
                imageResourceValue = drawable.ic_trophy,
                info = "${driverDetails.firstWin}",
                infoTag = stringResource(R.string.first_win)
            )
        } ?: StandingsBioItem(
            imageResourceValue = drawable.ic_trophy,
            info = "${driverDetails.firstPodium}",
            infoTag = stringResource(R.string.first_podium)
        )


        StandingsBioItem(
            imageResourceValue = drawable.ic_trophy,
            info = "${driverDetails.wdc}",
            infoTag = stringResource(R.string.world_championships)
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_calendar,
            info = driverDetails.driverInfo?.getOrNull(2) ?: "",
            infoTag = stringResource(R.string.date_of_birth)
        )

        StandingsBioItem(
            imageResourceValue = driverNationality,
            info = driverDetails.driverInfo?.getOrNull(3) ?: "",
            infoTag = stringResource(R.string.nationality)
        )

    }
}

@Composable fun ConstructorBioList(
    constructorDetails: ConstructorDetails,
){

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ){
        StandingsBioItem(
            imageResourceValue = drawable.ic_driver,
            info = constructorDetails.firstDriver?.getOrNull(1) ?: "",
            infoTag = constructorDetails.firstDriver?.getOrNull(2) ?: "",
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_driver,
            info = constructorDetails.secondDriver?.getOrNull(1) ?: "",
            infoTag = constructorDetails.secondDriver?.getOrNull(2) ?: "",
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_engine,
            info = constructorDetails.chassis?: "",
            infoTag = stringResource(R.string.chassis)
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_engine,
            info = constructorDetails.powerUnit?: "",
            infoTag = stringResource(R.string.power_unit)
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_person,
            info = "${constructorDetails.teamPrincipal}",
            infoTag = stringResource(R.string.team_principal)
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_trophy,
            info = "${constructorDetails.firstEntry}",
            infoTag = stringResource(R.string.first_entry)
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_trophy,
            info = "${constructorDetails.wcc}",
            infoTag = "Constructors Championship"
        )

        StandingsBioItem(
            imageResourceValue = drawable.ic_trophy,
            info = "${constructorDetails.wdc}",
            infoTag = "Drivers Championship"
        )

    }
}

@Composable
fun StandingsBioItem(
    imageResourceValue: Int?= null,
    imageUrl: String? = null,
    info: String,
    infoTag: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Img/Icon
        ImageComponent(
            modifier = Modifier
                .weight(1f)
                .size(18.dp),
            imageResourceValue = imageResourceValue,
            imageUrl = imageUrl,
            //colorFilter = ColorFilter.tint(color = AppTheme.colorScheme.onSecondary),
        )

        Column (
            modifier = Modifier.weight(6f)
        ){
            Text(
                text = info,
                style = AppTheme.typography.titleNormal,
                color = AppTheme.colorScheme.onSecondary,
            )
            Text(
                text = infoTag,
                style = AppTheme.typography.labelNormal,
                color = AppTheme.colorScheme.onSecondary,
            )
        }

    }

    Spacer(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(AppTheme.colorScheme.onBackground)
    )
}




