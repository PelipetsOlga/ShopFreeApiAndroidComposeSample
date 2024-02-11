package com.example.myapplication.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myapplication.ui.bottomMenu.BottomNavItem

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(route = BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavItem.Search.route) {
            SearchScreen()
        }
        composable(route = BottomNavItem.Favourites.route) {
            FavouritesScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    Text("Home")
}

@Composable
fun SearchScreen() {
    Text("Search")
}

@Composable
fun FavouritesScreen() {
    Text("Favourites")
}