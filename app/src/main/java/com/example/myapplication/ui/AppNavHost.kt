package com.example.myapplication.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.bottomMenu.Bag
import com.example.myapplication.ui.bottomMenu.Category
import com.example.myapplication.ui.bottomMenu.EditPaymentMethods
import com.example.myapplication.ui.bottomMenu.EditPersonalData
import com.example.myapplication.ui.bottomMenu.EditShippingAddress
import com.example.myapplication.ui.bottomMenu.Home
import com.example.myapplication.ui.bottomMenu.Product
import com.example.myapplication.ui.bottomMenu.Profile
import com.example.myapplication.ui.pdp.ProductScreen
import com.example.myapplication.ui.profile.payment.EditPaymentMethodsScreen
import com.example.myapplication.ui.profile.personal_data.EditPersonalDataScreen
import com.example.myapplication.ui.profile.ProfileScreen
import com.example.myapplication.ui.profile.shipping_address.EditShippingAddressScreen
import com.example.myapplication.ui.bag.BagScreen
import com.example.myapplication.ui.shop.categories.CategoriesScreen
import com.example.myapplication.ui.shop.category.CategoryProductsScreen

const val argPageName = "pageName"
private val animationSpec: FiniteAnimationSpec<Float> =
    spring(stiffness = Spring.StiffnessMediumLow)

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home.route,
    ) {
        composable(
            route = Home.route,
            arguments = listOf(navArgument(argPageName) { defaultValue = "Categories" }),
            enterTransition = { fadeIn(animationSpec) },
            exitTransition = { fadeOut(animationSpec) },
            popEnterTransition = { fadeIn(animationSpec) },
            popExitTransition = { fadeOut(animationSpec) },
        ) {
            CategoriesScreen(
                onCategoryClick = { categoryName ->
                    navController.navigateSingleTopTo(Category.getRouteWithArguments(categoryName))
                }
            )
        }
        composable(
            route = Bag.route,
            arguments = listOf(navArgument(argPageName) { defaultValue = "Bag" }),
            enterTransition = { fadeIn(animationSpec) },
            exitTransition = { fadeOut(animationSpec) },
            popEnterTransition = { fadeIn(animationSpec) },
            popExitTransition = { fadeOut(animationSpec) },
        ) {
            BagScreen()
        }
        composable(
            route = Profile.route,
            arguments = listOf(navArgument(argPageName) { defaultValue = "Profile" }),
            enterTransition = { fadeIn(animationSpec) },
            exitTransition = { fadeOut(animationSpec) },
            popEnterTransition = { fadeIn(animationSpec) },
            popExitTransition = { fadeOut(animationSpec) },
        ) {
            ProfileScreen(
                onEditPersonalData = { navController.navigate(EditPersonalData.route) },
                onEditShippingAddress = { navController.navigate(EditShippingAddress.route) },
                onEditPaymentMethods = { navController.navigate(EditPaymentMethods.route) }
            )
        }
        composable(
            route = EditPersonalData.route,
            arguments = EditPersonalData.arguments,
            enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            popEnterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
            popExitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
        ) {
            EditPersonalDataScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = EditShippingAddress.route,
            arguments = EditShippingAddress.arguments,
            enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            popEnterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
            popExitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
        ) {
            EditShippingAddressScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = EditPaymentMethods.route,
            arguments = EditPaymentMethods.arguments,
            enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            popEnterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
            popExitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
        ) {
            EditPaymentMethodsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Category.routeWithArguments,
            arguments = Category.arguments,
            enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            popEnterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
            popExitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
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
            enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) },
            popEnterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
            popExitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
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
