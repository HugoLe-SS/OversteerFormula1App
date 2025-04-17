package com.hugo.design.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hugo.design.R
import com.hugo.design.ui.theme.AppTheme

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

