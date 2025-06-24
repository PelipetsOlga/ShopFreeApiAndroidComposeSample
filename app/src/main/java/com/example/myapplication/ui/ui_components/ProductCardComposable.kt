package com.example.myapplication.ui.ui_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.domain.models.PRODUCT_MOCK
import com.example.myapplication.domain.models.Product

@Composable
fun ProductItemCard(
    product: Product,
    onProductClick: (String) -> Unit = {},
    onAddToBag: (Product) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onProductClick.invoke(product.id) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = "${product.title}. ${product.description}",
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${String.format("%.2f", product.price)}",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(36.dp),
                    onClick = { onAddToBag(product) }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = "Add to Bag",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onAddToBag: (Int) -> Unit = { }
) {
    var quantity by remember { mutableIntStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = "${product.title}. ${product.description}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Column(
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text(
                    text = product.title.uppercase(),
                    style = MaterialTheme.typography.headlineLarge
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuantityPicker(
                        count = quantity,
                        quantityCallback = { newQuantity ->
                            quantity = newQuantity
                        }
                    )

                    Text(
                        text = "$${String.format("%.2f", product.price)}",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.DarkGray
                    )
                }

                Button(
                    onClick = { onAddToBag(quantity) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "ADD TO BAG",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun previewProductItem() {
    ProductItemCard(PRODUCT_MOCK)
}

@Preview
@Composable
fun previewProduct() {
    ProductCard(PRODUCT_MOCK)
}