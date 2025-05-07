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
import com.hugo.oversteerf1.presentation.screens.home.HomeScreen
import com.hugo.schedule.presentation.screens.Details.CalendarResultScreen
import com.hugo.schedule.presentation.screens.Home.ScheduleHomeScreen
import com.hugo.standings.presentation.screens.Details.DriverDetails.StandingsDetailsScreen
import com.hugo.standings.presentation.screens.Home.StandingsHomeScreen
import com.hugo.utilities.com.hugo.utilities.Navigation.CalendarClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.CustomNavType.CalendarClickInfoNavType
import com.hugo.utilities.com.hugo.utilities.Navigation.Screen
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
                        AppLogger.d(message = "ScheduleHomeScreen round: $clickInfo")
                        navController.navigate(Screen.CalendarResultScreen(clickInfo)) // ✅ no string building
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
                    constructorCardClicked = { constructorId ->
                        AppLogger.d(message = "Standings Detail Screen constructorId: $constructorId")
                        navController.navigate(Screen.StandingsDetailsScreen(constructorId)) // ✅
                    },
                    driverCardClicked = { driverId ->
                        AppLogger.d(message = "Standings Detail Screen driverId: $driverId")
                        navController.navigate(Screen.StandingsDetailsScreen(driverId)) // ✅
                    }
                )
            }

            // Calendar Result Screen
            composable<Screen.CalendarResultScreen>(
                typeMap = mapOf(
                    typeOf<CalendarClickInfo>() to CalendarClickInfoNavType
                )
            ) { backStackEntry ->
                val screen: Screen.CalendarResultScreen = backStackEntry.toRoute()
                CalendarResultScreen(info = screen.info)
            }

            //Standings Details Screen
            composable<Screen.StandingsDetailsScreen> { backStackEntry ->
                val screen: Screen.StandingsDetailsScreen = backStackEntry.toRoute()
                StandingsDetailsScreen(driverId = screen.driverId, constructorId = screen.constructorId)
            }
        }
    }

}
