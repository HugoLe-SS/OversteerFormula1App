package com.hugo.design.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hugo.design.R
import com.hugo.design.ui.theme.AppTheme
import com.hugo.utilities.com.hugo.utilities.Navigation.Screen

data class BottomNavItem(
    val label: String? = null,
    val icon: Int,
    val screen: Screen,
)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem(label = stringResource(R.string.home), icon = R.drawable.ic_home, screen = Screen.HomeScreen),
        BottomNavItem(label = stringResource(R.string.schedule), icon = R.drawable.ic_calendar, screen =  Screen.ScheduleScreen),
        BottomNavItem(label = stringResource(R.string.standings), icon = R.drawable.ic_trophy, screen =  Screen.StandingsScreen)
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        containerColor = AppTheme.colorScheme.background,
        tonalElevation = 4.dp
    ) {
        bottomNavItems.forEach { item ->
            val destinationRoute = item.screen::class.simpleName
            val isSelected = currentDestination?.contains(destinationRoute ?: "") == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = "${item.label} icon",
                    )
                },

                label = {
                    item.label?.let {
                        Text(
                            text = it,
                            style = AppTheme.typography.labelMini
                        )
                    }
                },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colorScheme.onSecondary,
                    unselectedIconColor = AppTheme.colorScheme.onBackground,
                    selectedTextColor = AppTheme.colorScheme.onSecondary,
                    unselectedTextColor = AppTheme.colorScheme.onBackground,
                    indicatorColor = AppTheme.colorScheme.secondary.copy(alpha = 0.2f)
                )
            )
        }
    }
}
