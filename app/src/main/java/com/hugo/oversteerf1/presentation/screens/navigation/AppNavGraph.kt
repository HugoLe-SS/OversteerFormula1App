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

@Composable
fun AppNavGraph(){

    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()){
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route
        )
        {
            composable(
                route = Screen.HomeScreen.route,
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(500))
                }
            ){
                HomeScreen(
                    //setup Clickable actions here
                    navController = navController
                )
            }
        }
    }

}