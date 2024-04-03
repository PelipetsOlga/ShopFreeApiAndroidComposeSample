package com.example.myapplication.ui.pdp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.models.PRODUCT_EMPTY
import com.example.myapplication.ui.ui_components.ProductCard

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    productId: String
) {
    val product by viewModel.product.collectAsState(PRODUCT_EMPTY)

    LaunchedEffect(Unit) {
        Log.d("blabla", "LaunchedEffect fetchProduct productId=$productId")
        viewModel.fetchProduct(productId)
    }

    Column {
        if (product != PRODUCT_EMPTY) {
            ProductCard(product = product)
        }
    }
}