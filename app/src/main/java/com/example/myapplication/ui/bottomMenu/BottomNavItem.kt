package com.example.myapplication.ui.bottomMenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.myapplication.ui.argPageName

private const val HOME = "home"
private const val BAG = "bag"
private const val PROFILE = "profile"
private const val EDIT_PERSONAL_DATA = "edit_personal_data"
private const val EDIT_SHIPPING_ADDRESS = "edit_shipping_address"
private const val EDIT_PAYMENT_METHODS = "edit_payment_methods"
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

data object Profile : BottomNavItem {
    override val route = PROFILE
    override val icon = Icons.Default.Person
    override val label = "Profile"
}

data object EditPersonalData : BottomNavItem {
    override val route = EDIT_PERSONAL_DATA
    override val arguments = listOf(navArgument(argPageName) { defaultValue = "Edit Personal Data" })
}

data object EditShippingAddress : BottomNavItem {
    override val route = EDIT_SHIPPING_ADDRESS
    override val arguments = listOf(navArgument(argPageName) { defaultValue = "Edit Shipping Address" })
}

data object EditPaymentMethods : BottomNavItem {
    override val route = EDIT_PAYMENT_METHODS
    override val arguments = listOf(navArgument(argPageName) { defaultValue = "Edit Payment" })
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
