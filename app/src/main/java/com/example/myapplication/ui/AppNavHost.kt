package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.bottomMenu.Category
import com.example.myapplication.ui.bottomMenu.Favourites
import com.example.myapplication.ui.bottomMenu.Home
import com.example.myapplication.ui.bottomMenu.Product
import com.example.myapplication.ui.bottomMenu.Search
import com.example.myapplication.ui.favourites.FavouritesScreen
import com.example.myapplication.ui.pdp.ProductScreen
import com.example.myapplication.ui.search.SearchScreen
import com.example.myapplication.ui.shop.categories.CategoriesScreen
import com.example.myapplication.ui.shop.category.CategoryProductsScreen

const val argPageName = "pageName"

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Home.route
    ) {
        composable(route = Home.route, arguments = listOf(navArgument(argPageName) { defaultValue = "Categories" })) {
            CategoriesScreen(
                onCategoryClick = { categoryName ->
                    navController.navigateSingleTopTo(Category.getRouteWithArguments(categoryName))
                }
            )
        }
        composable(route = Search.route, arguments = listOf(navArgument(argPageName) { defaultValue = "Search" })) {
            SearchScreen()
        }
        composable(route = Favourites.route, arguments = listOf(navArgument(argPageName) { defaultValue = "Favorites" })) {
            FavouritesScreen()
        }
        composable(
            route = Category.routeWithArguments,
            arguments = Category.arguments,
        ) { navBackStackEntry ->
            val categoryName =
                navBackStackEntry.arguments?.getString(Category.argTag)

            CategoryProductsScreen(categoryName = categoryName ?: "Test category",
                onProductClick = { productId ->
                    navController.navigate(Product.getRouteWithArguments(productId))
                })
        }
        composable(
            route = Product.routeWithArguments,
            arguments = Product.arguments,
        ) { navBackStackEntry ->
            val productId =
                navBackStackEntry.arguments?.getString(Product.argTag)

            ProductScreen(productId = productId ?: "Empty PDP")
        }
    }
}

private fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
