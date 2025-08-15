package com.hugo.oversteerf1.presentation.components

import androidx.compose.runtime.Composable
import com.hugo.datasource.local.entity.F1HomeDetails
import com.hugo.design.components.HorizontalPager

@Composable
fun HorizontalPagerItem(
    f1HomeDetails: F1HomeDetails,
    onClicked: (Int) -> Unit = {}
){
    val imageUrls = listOf(
        f1HomeDetails.driverImg,
        f1HomeDetails.raceImg,
        f1HomeDetails.constructorStandingsImg,
        f1HomeDetails.driverStandingsImg
    )

    HorizontalPager(
        imageUrls = imageUrls,
        onClicked = onClicked
    )
}