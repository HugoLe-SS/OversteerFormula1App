package com.hugo.oversteerf1.presentation.screens.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

object NavTransitions {
    private const val DEFAULT_DURATION_MS = 300

    // These functions now RETURN the lambda that defines the transition
    fun slideInFromRight(duration: Int = DEFAULT_DURATION_MS): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) {
        return {
            slideIntoContainer( // Now callable because 'this' is AnimatedContentTransitionScope
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = duration)
            )
        }
    }

    fun slideOutToLeft(duration: Int = DEFAULT_DURATION_MS): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) {
        return {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = duration)
            )
        }
    }

    fun slideInFromLeft(duration: Int = DEFAULT_DURATION_MS): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) {
        return {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = duration)
            )
        }
    }

    fun slideOutToRight(duration: Int = DEFAULT_DURATION_MS): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) {
        return {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = duration)
            )
        }
    }

    // Example with combined transitions
    fun slideInFromRightWithFade(duration: Int = DEFAULT_DURATION_MS): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) {
        return {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = duration)
            ) + fadeIn(animationSpec = tween(durationMillis = duration))
        }
    }

    fun slideOutToLeftWithFade(duration: Int = DEFAULT_DURATION_MS): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) {
        return {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = duration)
            ) + fadeOut(animationSpec = tween(durationMillis = duration))
        }
    }

}