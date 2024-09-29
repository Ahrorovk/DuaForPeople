package com.ahrorovk.duaforpeople.app.navigation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ahrorovk.duaforpeople.core.util.Routes
import com.ahrorovk.duaforpeople.core.util.models.BottomNavDestination

@Composable
fun RowScope.BottomNavItem(
    navController: NavController,
    item: BottomNavDestination
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            when (item.destinationRoute) {
                Routes.MainScreen.route -> {
                    it.route == Routes.MainScreen.route
                }

                Routes.SenderRequestScreen.route -> {
                    it.route == Routes.SenderRequestScreen.route
                }
                Routes.UserScreen.route -> {
                    it.route == Routes.UserScreen.route
                }

                else -> {
                    it.route == Routes.MainScreen.route
                }
            }
        } == true,
        onClick = {
            if (currentDestination?.route != item.destinationRoute)
                navigateToScreen(item.destinationRoute, navController)
        },
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = item.icon,
                contentDescription = "BottomNavIcon"
            )
        },
        selectedContentColor = MaterialTheme.colorScheme.background,
        unselectedContentColor = MaterialTheme.colorScheme.onBackground
    )
}

private fun navigateToScreen(route: String, navController: NavController) {
    navController.navigate(route = route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}