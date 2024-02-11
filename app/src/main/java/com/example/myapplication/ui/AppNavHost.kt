package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.bottomMenu.BottomNavItem
import com.example.myapplication.ui.favourites.FavouritesScreen
import com.example.myapplication.ui.search.SearchScreen
import com.example.myapplication.ui.shop.categories.CategoriesScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(route = BottomNavItem.Home.route) {
            CategoriesScreen()
        }
        composable(route = BottomNavItem.Search.route) {
            SearchScreen()
        }
        composable(route = BottomNavItem.Favourites.route) {
            FavouritesScreen()
        }
    }
}
