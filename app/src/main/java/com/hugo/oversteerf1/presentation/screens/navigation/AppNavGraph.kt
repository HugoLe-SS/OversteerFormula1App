package com.hugo.oversteerf1.presentation.screens.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.hugo.authentication.presentation.screens.AuthScreen
import com.hugo.datasource.local.entity.Schedule.F1CalendarInfo
import com.hugo.datasource.local.entity.Schedule.F1CircuitDetails
import com.hugo.oversteerf1.presentation.screens.home.HomeScreen
import com.hugo.result.presentation.screens.ResultScreen
import com.hugo.schedule.presentation.screens.Details.CalendarDetailsScreen
import com.hugo.schedule.presentation.screens.Home.ScheduleHomeScreen
import com.hugo.standings.presentation.screens.Details.StandingsDetailsScreen
import com.hugo.standings.presentation.screens.Home.StandingsHomeScreen
import com.hugo.utilities.com.hugo.utilities.Navigation.CustomNavType
import com.hugo.utilities.com.hugo.utilities.Navigation.Screen
import com.hugo.utilities.com.hugo.utilities.Navigation.model.ConstructorClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.model.DriverClickInfo
import com.hugo.utilities.logging.AppLogger
import kotlin.reflect.typeOf

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    //val animationSpec = tween<IntOffset>(durationMillis = 300)
    val fadeSpec = tween<Float>(durationMillis = 500)

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            //startDestination = Screen.HomeScreen
            startDestination = Screen.AuthScreen
        ) {

            // App Home Screen
            composable<Screen.HomeScreen>(
                enterTransition = { fadeIn(animationSpec = fadeSpec) },
                exitTransition = { fadeOut(animationSpec = fadeSpec) },
                popEnterTransition = { fadeIn(animationSpec = fadeSpec) },
                popExitTransition = { fadeOut(animationSpec = fadeSpec) }
            ) {
                HomeScreen(
                    navController = navController,
                    cardOnClicked = {
                        navController.navigate(Screen.ScheduleScreen) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    bannerOnClicked = { index ->
                        when(index){
                            0,2,3 ->  navController.navigate(Screen.StandingsScreen) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            1 ->  navController.navigate(Screen.ScheduleScreen) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                    }
                )
            }

            // Auth Screen
            composable<Screen.AuthScreen>(
                enterTransition = { fadeIn(animationSpec = fadeSpec) },
                exitTransition = { fadeOut(animationSpec = fadeSpec) },
                popEnterTransition = { fadeIn(animationSpec = fadeSpec) },
                popExitTransition = { fadeOut(animationSpec = fadeSpec) }
            ) {
                AuthScreen()
            }

            // Schedule Home Screen
            composable<Screen.ScheduleScreen>(
                enterTransition = { fadeIn(animationSpec = fadeSpec) },
                exitTransition = { fadeOut(animationSpec = fadeSpec) },
                popEnterTransition = { fadeIn(animationSpec = fadeSpec) },
                popExitTransition = { fadeOut(animationSpec = fadeSpec) }
            ) {
                ScheduleHomeScreen(
                    viewScheduleButtonClicked = { clickInfo ->
                        AppLogger.d(message = "ScheduleHomeScreen circuit: ${clickInfo.circuitId}")
                        navController.navigate(Screen.CalendarDetailsScreen(clickInfo))
                    },
                    cardClicked = { clickInfo ->
                        AppLogger.d(message = "ScheduleHomeScreen circuit: ${clickInfo.circuitId}")
                        navController.navigate(Screen.CalendarDetailsScreen(clickInfo))
                    },
                    navController = navController
                )
            }

            // Standings Home Screen
            composable<Screen.StandingsScreen>(
                enterTransition = { fadeIn(animationSpec = fadeSpec) },
                exitTransition = { fadeOut(animationSpec = fadeSpec) },
                popEnterTransition = { fadeIn(animationSpec = fadeSpec) },
                popExitTransition = { fadeOut(animationSpec = fadeSpec) }
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
                ),
                enterTransition = NavTransitions.slideInFromRight(),
                exitTransition = NavTransitions.slideOutToLeft(),
                popEnterTransition = NavTransitions.slideInFromLeft(),
                popExitTransition = NavTransitions.slideOutToRight()
            ) { backStackEntry ->
                val screen: Screen.CalendarDetailsScreen = backStackEntry.toRoute()
                CalendarDetailsScreen(
                    backButtonClicked = {navController.popBackStack()},
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
                ),
                enterTransition = NavTransitions.slideInFromRight(),
                exitTransition = NavTransitions.slideOutToLeft(),
                popEnterTransition = NavTransitions.slideInFromLeft(),
                popExitTransition = NavTransitions.slideOutToRight()
            ) { backStackEntry ->
                val screen: Screen.StandingsDetailsScreen = backStackEntry.toRoute()
                StandingsDetailsScreen(
                    backButtonClicked = {navController.popBackStack()},
                    constructorClickInfo = screen.constructorClickInfo,
                    driverClickInfo = screen.driverClickInfo,
                    viewResultButtonClicked = { driverId, constructorId ->
                        AppLogger.d(message = "View Result Button Clicked: ${driverId?: constructorId} ")
                        navController.navigate(Screen.ResultScreen(driverId = driverId, constructorId = constructorId))
                    },
                )
            }

            // Result Screen
            composable<Screen.ResultScreen>(
                typeMap = mapOf(
                    typeOf<F1CircuitDetails?>() to CustomNavType.CircuitDetailsNavType
                ),
                enterTransition = NavTransitions.slideInFromRight(),
                exitTransition = NavTransitions.slideOutToLeft(),
                popEnterTransition = NavTransitions.slideInFromLeft(),
                popExitTransition = NavTransitions.slideOutToRight()
            ) { backStackEntry ->
                val screen: Screen.ResultScreen = backStackEntry.toRoute()
                ResultScreen(
                    backButtonClicked = {navController.popBackStack()},
                    driverId = screen.driverId,
                    constructorId = screen.constructorId,
                    circuitDetails = screen.circuitDetails
                )
            }

        }
    }

}


