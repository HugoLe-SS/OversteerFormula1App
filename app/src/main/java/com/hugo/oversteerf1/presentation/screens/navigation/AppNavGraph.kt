package com.hugo.oversteerf1.presentation.screens.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.oversteerf1.presentation.screens.home.HomeScreen
import com.hugo.result.presentation.screens.ResultScreen
import com.hugo.schedule.presentation.screens.Details.CalendarDetailsScreen
import com.hugo.schedule.presentation.screens.Home.ScheduleHomeScreen
import com.hugo.standings.presentation.screens.Details.StandingsDetailsScreen
import com.hugo.standings.presentation.screens.Home.StandingsHomeScreen
import com.hugo.utilities.com.hugo.utilities.Navigation.CustomNavType
//import com.hugo.utilities.com.hugo.utilities.Navigation.CustomNavType.CalendarClickInfoNavType
import com.hugo.utilities.com.hugo.utilities.Navigation.Screen
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo
import com.hugo.utilities.logging.AppLogger
import kotlin.reflect.typeOf

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen
        ) {

            // App Home Screen
            composable<Screen.HomeScreen>(
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200)
                    )
                }
            ) {
                HomeScreen(
                    navController = navController
                )
            }

            // Schedule Home Screen
            composable<Screen.ScheduleScreen>(
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200)
                    )
                }
            ) {
                ScheduleHomeScreen(
                    cardClicked = { clickInfo ->
                        AppLogger.d(message = "ScheduleHomeScreen circuit: ${clickInfo.circuitId}")
                        navController.navigate(Screen.CalendarDetailsScreen(clickInfo))
                    },
                    navController = navController
                )
            }

            // Standings Home Screen
            composable<Screen.StandingsScreen>(
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200)
                    )
                }
            ) {
                StandingsHomeScreen(
                    navController = navController,
                    constructorCardClicked = { constructorClickInfo ->
                        AppLogger.d(message = "Standings Detail Screen constructorId: ${constructorClickInfo.constructorId}")
                        navController.navigate(Screen.StandingsDetailsScreen(constructorClickInfo = constructorClickInfo, driverClickInfo = null))
                    },
                    driverCardClicked = { driverClickInfo ->
                        AppLogger.d(message = "Standings Detail Screen driverId: ${driverClickInfo.driverId}")
                        navController.navigate(Screen.StandingsDetailsScreen(constructorClickInfo = null, driverClickInfo = driverClickInfo))
                    }
                )
            }

            // Calendar Details Screen
            composable<Screen.CalendarDetailsScreen>(
                typeMap = mapOf(
                    typeOf<F1CalendarInfo>() to CustomNavType.CalendarClickInfoNavType
                )
            ) { backStackEntry ->
                val screen: Screen.CalendarDetailsScreen = backStackEntry.toRoute()
                CalendarDetailsScreen(
                    calendarInfo = screen.info,
                    viewResultButtonClicked = { f1CircuitDetails ->
                        AppLogger.d(message = "CircuitID: ${f1CircuitDetails.circuitId}")
                        navController.navigate(Screen.ResultScreen(circuitDetails = f1CircuitDetails))
                    }
                )
            }

            // Standings Details Screen
            composable<Screen.StandingsDetailsScreen>(
                typeMap = mapOf(
                    typeOf<ConstructorClickInfo?>() to CustomNavType.ConstructorClickInfoNavType,
                    typeOf<DriverClickInfo?>() to CustomNavType.DriverClickInfoNavType
                )
            ) { backStackEntry ->
                val screen: Screen.StandingsDetailsScreen = backStackEntry.toRoute()
                StandingsDetailsScreen(
                    constructorClickInfo = screen.constructorClickInfo,
                    driverClickInfo = screen.driverClickInfo,
                    viewResultButtonClicked = { id ->
                        AppLogger.d(message = "View Result Button Clicked: $id")
                        navController.navigate(Screen.ResultScreen(id))
                    },
                )
            }

            // Result Screen
            composable<Screen.ResultScreen>(
                typeMap = mapOf(
                    typeOf<F1CircuitDetails?>() to CustomNavType.CircuitDetailsNavType
                ),
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200)
                    )
                }
            ) { backStackEntry ->
                val screen: Screen.ResultScreen = backStackEntry.toRoute()
                ResultScreen(
                    raceId = screen.driverId?: screen.constructorId,
                    circuitDetails = screen.circuitDetails
                )
            }

        }
    }

}


