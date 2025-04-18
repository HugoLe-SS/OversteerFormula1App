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
import com.hugo.oversteerf1.presentation.screens.home.HomeScreen
import com.hugo.schedule.presentation.screens.ScheduleHomeScreen
import com.hugo.standings.presentation.screens.Home.StandingsHomeScreen
import com.hugo.utilities.Screen

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
                    navController = navController
                )
            }
        }
    }



}