package com.example.myapplication.ui.bottomMenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.myapplication.ui.argPageName

private const val HOME = "home"
private const val BAG = "bag"
private const val FAVOURITES = "favourites"
private const val CATEGORY = "category"
private const val PRODUCT = "product"
private val homeIcon = Icons.Default.Home

sealed interface BottomNavItem {
    val route: String
    val icon: ImageVector
        get() = homeIcon
    val label: String
        get() = ""
    val arguments: List<NamedNavArgument>?
        get() = null
    val routeWithArguments: String?
        get() = null
}

data object Home : BottomNavItem {
    override val route = HOME
    override val icon = homeIcon
    override val label = "Home"
}

data object Bag : BottomNavItem {
    override val route = BAG
    override val icon = Icons.Default.ShoppingBag
    override val label = "Bag"
}

data object Favourites : BottomNavItem {
    override val route = FAVOURITES
    override val icon = Icons.Default.Favorite
    override val label = "Favourites"
}

data object Category : BottomNavItem {
    override val route = CATEGORY
    const val argTag = "category_name"
    override val routeWithArguments = "${route}/{$argTag}"
    override val arguments = listOf(navArgument(argTag) { type = NavType.StringType }, navArgument(argPageName) { defaultValue = "Category" })
    fun getRouteWithArguments(categoryName: String) = "${route}/$categoryName"
}

data object Product : BottomNavItem {
    override val route = PRODUCT
    const val argTag = "product"
    override val routeWithArguments = "${route}/{$argTag}"
    override val arguments = listOf(navArgument(argTag) { type = NavType.StringType }, navArgument(argPageName) { defaultValue = "Product" })
    fun getRouteWithArguments(productId: String) = "${route}/$productId"
}
