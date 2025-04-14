package com.hugo.oversteerf1.presentation.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hugo.design.ui.theme.AppTheme
import com.hugo.oversteerf1.R

@Composable
fun BottomNavBar(
    //items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(35.dp)),
        containerColor = AppTheme.colorScheme.onBackground,
        //contentColor = AppTheme.colorScheme.onSecondary,
        tonalElevation = 4.dp
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route, // Highlight the selected item
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = AppTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(24.dp),
                        )
                },
                label = {
                    Text(
                        text = item.label,
                        style = AppTheme.typography.labelSmall,
                        color = AppTheme.colorScheme.onSecondary
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home, Screen.HomeScreen.route),
        BottomNavItem("Schedule", R.drawable.ic_calendar, Screen.ScheduleScreen.route),
        BottomNavItem("Standings", R.drawable.ic_trophy, Screen.StandingsScreen.route),
        BottomNavItem("News", R.drawable.ic_news, Screen.NewsScreen.route)
    )
    val navController = NavController(context = LocalContext.current)

    AppTheme{
    BottomNavBar(navController = navController)
    }
}
