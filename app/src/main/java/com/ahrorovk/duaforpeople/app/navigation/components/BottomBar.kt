package com.ahrorovk.duaforpeople.app.navigation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavController
import com.ahrorovk.duaforpeople.core.util.Routes
import com.ahrorovk.duaforpeople.core.util.models.BottomNavDestination

@Composable
fun DuaForPeopleBottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = White.copy(alpha = 0.95F),
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        bottomNavDestinations.forEach { navItem ->
            BottomNavItem(navController = navController, item = navItem)
        }

    }
}

val bottomNavDestinations = listOf(
    BottomNavDestination(
        label = "Main",
        destinationRoute = Routes.MainScreen.route,
        icon = Icons.Default.Home
    ),
    BottomNavDestination(
        label = "Dua",
        destinationRoute = Routes.SenderRequestScreen.route,
        icon = Icons.Default.Favorite
    ),
    BottomNavDestination(
        label = "Profile",
        destinationRoute = Routes.UserScreen.route,
        icon = Icons.Default.Person
    )
)