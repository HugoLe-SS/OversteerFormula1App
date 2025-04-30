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
import com.hugo.standings.presentation.screens.Details.ConstructorDetails.ConstructorDetailsScreen
import com.hugo.standings.presentation.screens.Details.DriverDetails.DriverDetailsScreen
import com.hugo.standings.presentation.screens.Home.StandingsHomeScreen
import com.hugo.utilities.com.hugo.utilities.Navigation.CalendarClickInfo
import com.hugo.utilities.com.hugo.utilities.Navigation.CustomNavType.CalendarClickInfoNavType
import com.hugo.utilities.com.hugo.utilities.Navigation.Screen
import com.hugo.utilities.logging.AppLogger
import kotlin.reflect.typeOf

//@Composable
//fun AppNavGraph(){
//
//    val navController = rememberNavController()
//
//    Surface(modifier = Modifier.fillMaxSize()){
//
//        NavHost(
//            navController = navController,
//            startDestination = Screen.HomeScreen.route
//        )
//        {
//
//            //App Home Screen
//            composable(
//                route = Screen.HomeScreen.route,
//                popEnterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right,
//                        tween(200))
//                },
//                exitTransition = {
//                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left,
//                        tween(200)
//                    )
//                }
//
//            ){
//                HomeScreen(
//                    //setup Clickable actions here
//                    navController = navController
//                )
//            }
//
//            //Schedule Home Screen
//            composable(
//                route = Screen.ScheduleScreen.route,
//                popEnterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        tween(200))
//                },
//                exitTransition = {
//                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
//                        tween(200)
//                    )
//                }
//            ){
//                ScheduleHomeScreen(
//                    //setup Clickable actions here
////                    cardClicked = { round ->
////                        navController.navigate(Screen.CalendarResultScreen.route + "/$round")
////                    },
//                    cardClicked = { info ->
//                        navController.navigate(
//                            Screen.CalendarResultScreen.route + "/${info.round}/${info.circuitId}"
//                        )
//                    },
//
//                    navController = navController
//                )
//            }
//
//            //Standings Home Screen
//            composable(
//                route = Screen.StandingsScreen.route,
//                popEnterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        tween(200))
//                },
//                exitTransition = {
//                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
//                        tween(200)
//                    )
//                }
//            ){
//                StandingsHomeScreen(
//                    //setup Clickable actions here
//                    navController = navController,
//                    constructorCardClicked = { constructorId ->
//                        navController.navigate(Screen.ConstructorDetailsScreen.route + "/$constructorId")
//                    },
//                    driverCardClicked = { driverId ->
//                        navController.navigate(Screen.DriverDetailsScreen.route + "/$driverId")
//                    }
//                )
//            }
//
//            //Driver Details Screen
//            composable(
//                route = Screen.DriverDetailsScreen.route + "/{${ScreenArguments.DRIVER_ID}}",
//                enterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        tween(200))
//                },
//                exitTransition = {
//                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
//                        tween(200)
//                    )
//                },
//                arguments = listOf(
//                    navArgument(name = "${ScreenArguments.DRIVER_ID}") {
//                        type = NavType.StringType}
//                )
//            ){
//                //get the driverId from the arguments (from StandingsHomeScreen to DriverDetailsScreen)
//                it?.arguments?.getString("${ScreenArguments.DRIVER_ID}")?.also{ driverId ->
//                    AppLogger.d(message = "DriverDetailsScreen driverId: $driverId")
//                    DriverDetailsScreen(
//                        driverId = driverId,
//                        backButtonClicked = {
//                            navController.popBackStack()
//                        }
//                    )
//                }
//
//            }
//
//            //Constructor Details Screen
//            composable(
//                route = Screen.ConstructorDetailsScreen.route + "/{${ScreenArguments.CONSTRUCTOR_ID}}",
//                enterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        tween(200))
//                },
//                exitTransition = {
//                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
//                        tween(200)
//                    )
//                },
//                arguments = listOf(
//                    navArgument(name = "${ScreenArguments.CONSTRUCTOR_ID}") {
//                        type = NavType.StringType}
//                )
//
//            ){
//                //get the constructorId from the arguments (from StandingsHomeScreen to ConstructorDetailsScreen)
//                it?.arguments?.getString("${ScreenArguments.CONSTRUCTOR_ID}")?.also{ constructorId ->
//                    AppLogger.d(message = "ConstructorDetailsScreen constructorId: $constructorId")
//                    ConstructorDetailsScreen(
//                        constructorId = constructorId,
//                        backButtonClicked = {
//                            navController.popBackStack()
//                        }
//                    )
//                }
//
//            }
//
//            //Calendar Result Screen
////            composable(
////                route = Screen.CalendarResultScreen.route + "/{${ScreenArguments.ROUND}/{${ScreenArguments.CIRCUIT_ID}}",
////
////                arguments = listOf(
////                    navArgument(name = "${ScreenArguments.ROUND}") {
////                        type = NavType.StringType},
////
////                    navArgument(name = "${ScreenArguments.CIRCUIT_ID}") {
////                        type = NavType.StringType
////                    }
////                ),
////
////                enterTransition = {
////                    slideIntoContainer(
////                        AnimatedContentTransitionScope.SlideDirection.Left,
////                        tween(200))
////                },
////                exitTransition = {
////                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
////                        tween(200)
////                    )
////                }
////            ){
////                //get the round from the arguments (from ScheduleHomeScreen to CalendarResultScreen)
//////                it?.arguments?.getString("${ScreenArguments.ROUND}")?.also{ round ->
//////                    AppLogger.d(message = "CalendarResultScreen round: $round")
//////                    CalendarResultScreen(
//////                        round = round,
//////                        circuitId = circuitId,
//////                        backButtonClicked = {
//////                            navController.popBackStack()
//////                        }
//////                    )
////
////                backStackEntry ->
////                val round = backStackEntry.arguments?.getString("${ScreenArguments.ROUND}")
////                val circuitId = backStackEntry.arguments?.getString("${ScreenArguments.CIRCUIT_ID}")
////
////                if (round != null && circuitId != null) {
////                    AppLogger.d(message = "CalendarResultScreen round: $round, circuitId: $circuitId")
////                    CalendarResultScreen(
////                        round = round,
////                        circuitId = circuitId,
////                        backButtonClicked = {
////                            navController.popBackStack()
////                        }
////                    )
////                }
////
////            }
//
//            composable(
//                route = Screen.CalendarResultScreen.route,
//                arguments = listOf(
//                    navArgument(ScreenArguments.ROUND.name) {
//                        type = NavType.StringType
//                    },
//                    navArgument(ScreenArguments.CIRCUIT_ID.name) {
//                        type = NavType.StringType
//                    }
//                ),
//                enterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left,
//                        tween(200))
//                },
//                exitTransition = {
//                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,
//                        tween(200)
//                    )
//                }
//
//            ) { backStackEntry ->
//                val round = backStackEntry.arguments?.getString(ScreenArguments.ROUND.name)
//                val circuitId = backStackEntry.arguments?.getString(ScreenArguments.CIRCUIT_ID.name)
//
//                if (round != null && circuitId != null) {
//                    CalendarResultScreen(
//                        round = round,
//                        circuitId = circuitId,
//                        backButtonClicked = {
//                            navController.popBackStack()
//                        }
//                    )
//                }
//            }
//
//
//        }
//    }
//}

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
                        AppLogger.d(message = "ConstructorDetailsScreen constructorId: $constructorId")
                        navController.navigate(Screen.ConstructorDetailsScreen(constructorId)) // ✅
                    },
                    driverCardClicked = { driverId ->
                        AppLogger.d(message = "DriverDetailsScreen driverId: $driverId")
                        navController.navigate(Screen.DriverDetailsScreen(driverId)) // ✅
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

            // Constructor Details Screen
            composable<Screen.ConstructorDetailsScreen> { backStackEntry ->
                val screen: Screen.ConstructorDetailsScreen = backStackEntry.toRoute()
                ConstructorDetailsScreen(constructorId = screen.constructorId)
            }

            // Driver Details Screen
            composable<Screen.DriverDetailsScreen> { backStackEntry ->
                val screen: Screen.DriverDetailsScreen = backStackEntry.toRoute()
                DriverDetailsScreen(driverId = screen.driverId)
            }
        }
    }

}
