package com.example.myapplication.ui.shop.category

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.models.Product
import com.example.myapplication.ui.ui_components.ProductItemCard

@Composable
fun CategoryProductsScreen(
    viewModel: CategoryProductsViewModel = hiltViewModel(),
    categoryName: String,
    onProductClick: (String) -> Unit = {}
) {
    val products by viewModel.products.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        Log.d("blabla", "LaunchedEffect fetchProducts categoryName=$categoryName")
        viewModel.fetchProducts(categoryName)
    }

    Column {
        LazyColumn {
            items(products.size, key = { index -> products[index].id }) { index ->
                ProductItemCard(product = products[index], onProductClick = onProductClick)
            }
        }
    }
}