package com.hugo.oversteerf1.presentation.screens.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hugo.oversteerf1.presentation.screens.home.HomeScreen
import com.hugo.schedule.presentation.screens.ScheduleHomeScreen
import com.hugo.standings.presentation.screens.Details.ConstructorDetails.ConstructorDetailsScreen
import com.hugo.standings.presentation.screens.Details.DriverDetails.DriverDetailsScreen
import com.hugo.standings.presentation.screens.Home.StandingsHomeScreen
import com.hugo.utilities.Screen
import com.hugo.utilities.ScreenArguments
import com.hugo.utilities.logging.AppLogger

@Composable
fun AppNavGraph(){

    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()){

        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route
        )
        {

            //App Home Screen
            composable(
                route = Screen.HomeScreen.route,
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200)
                    )
                }

            ){
                HomeScreen(
                    //setup Clickable actions here
                    navController = navController
                )
            }

            //Schedule Home Screen
            composable(
                route = Screen.ScheduleScreen.route,
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200)
                    )
                }
            ){
                ScheduleHomeScreen(
                    //setup Clickable actions here
                    navController = navController
                )
            }

            //Standings Home Screen
            composable(
                route = Screen.StandingsScreen.route,
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200)
                    )
                }
            ){
                StandingsHomeScreen(
                    //setup Clickable actions here
                    navController = navController,
                    constructorCardClicked = { constructorId ->
                        navController.navigate(Screen.ConstructorDetailsScreen.route + "/$constructorId")
                    },
                    driverCardClicked = { driverId ->
                        navController.navigate(Screen.DriverDetailsScreen.route + "/$driverId")
                    }
                )
            }

            //Driver Details Screen
            composable(
                route = Screen.DriverDetailsScreen.route + "/{${ScreenArguments.DRIVER_ID}}",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200)
                    )
                },
                arguments = listOf(
                    navArgument(name = "${ScreenArguments.DRIVER_ID}") {
                        type = NavType.StringType}
                )
            ){
                //get the coinId from the arguments (from WealthHomeScreen to CoinDetailsScreen)
                it?.arguments?.getString("${ScreenArguments.DRIVER_ID}")?.also{ driverId ->
                    AppLogger.d(message = "DriverDetailsScreen driverId: $driverId")
                    DriverDetailsScreen(
                        driverId = driverId,
                        backButtonClicked = {
                            navController.popBackStack()
                        }
                    )
                }

            }

            //Constructor Details Screen
            composable(
                route = Screen.ConstructorDetailsScreen.route + "/{${ScreenArguments.CONSTRUCTOR_ID}}",
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(200))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(200)
                    )
                },
                arguments = listOf(
                    navArgument(name = "${ScreenArguments.CONSTRUCTOR_ID}") {
                        type = NavType.StringType}
                )

            ){
                //get the coinId from the arguments (from WealthHomeScreen to CoinDetailsScreen)
                it?.arguments?.getString("${ScreenArguments.CONSTRUCTOR_ID}")?.also{ constructorId ->
                    AppLogger.d(message = "ConstructorDetailsScreen constructorId: $constructorId")
                    ConstructorDetailsScreen(
                        constructorId = constructorId,
                        backButtonClicked = {
                            navController.popBackStack()
                        }
                    )
                }

            }
        }
    }



}