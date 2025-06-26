package com.example.myapplication.ui.bottomMenu

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.cux.analytics_sdk.composable.monitorElement

@Composable
fun BottomNavBar(navController: NavHostController) {
    val screens = listOf(
        Home,
        Bag,
        Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination?.route == Home.route
        || currentDestination?.route == Category.route
        || currentDestination?.route == Bag.route
        || currentDestination?.route == Profile.route
    ) {

        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            screens.forEach { screen ->
                val currentRoute = navBackStackEntry?.destination?.route
                val selected = (currentRoute == screen.route)
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    selected = selected,
                )
            }
        }
    } else {
        // Hide Bottom Navigation
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavHostController,
    selected: Boolean,
) {
    BottomNavigationItem(
        modifier = Modifier.monitorElement("tab_${screen.label}"),
        label = {
            Text(
                text = screen.label,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = if (selected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                ),
            )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint = if (selected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
            )
        },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        unselectedContentColor = MaterialTheme.colorScheme.outline,
        selectedContentColor = MaterialTheme.colorScheme.onPrimary,

        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

fun NavDestination.showBottomBar() {

}