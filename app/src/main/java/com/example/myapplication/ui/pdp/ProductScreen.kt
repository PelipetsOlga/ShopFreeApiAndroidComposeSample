package com.example.myapplication.ui.pdp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (product != PRODUCT_EMPTY) {
            ProductCard(
                product = product,
                onAddToBag = { quantity -> viewModel.addToBag(product, quantity) }
            )
        }
    }
}