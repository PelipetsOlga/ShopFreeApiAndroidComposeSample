package com.example.myapplication.ui.ui_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.domain.models.PRODUCT_MOCK
import com.example.myapplication.domain.models.Product

@Composable
fun ProductItemCard(product: Product, onProductClick: (String) -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onProductClick.invoke(product.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = "${product.title}. ${product.description}",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(product.title, fontWeight = FontWeight.Bold)
                Text("$${product.price}")
            }

        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        AsyncImage(
            model = product.image,
            contentDescription = "${product.title}. ${product.description}",
            modifier = Modifier.fillMaxWidth(),
        )
        Text(product.title, fontWeight = FontWeight.Bold)
        Text("$${product.price}")
        Text(product.description, fontWeight = FontWeight.Light)
    }
}

//@Preview
//@Composable
//fun previewProductItem() {
//    ProductItemCard(PRODUCT_MOCK)
//}

@Preview
@Composable
fun previewProduct() {
    ProductCard(PRODUCT_MOCK)
}