package com.example.myapplication.ui.pdp

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.shop.category.CategoryProductsViewModel

@Composable
fun ProductScreen(
    viewModel: CategoryProductsViewModel = hiltViewModel(),
    productId: String
) {
    Text("Product id = $productId")
}