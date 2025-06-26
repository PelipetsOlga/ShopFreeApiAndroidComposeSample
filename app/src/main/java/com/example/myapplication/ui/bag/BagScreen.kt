package com.example.myapplication.ui.bag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.cux.analytics_sdk.composable.monitorElement

@Composable
fun BagScreen(
    modifier: Modifier = Modifier,
    viewModel: BagViewModel = hiltViewModel()
) {
    val bag by viewModel.bag.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BagSummaryCard(bag = bag)
        }

        items(bag.items, key = { it.product.id }) { bagItem ->
            RemovableBagItemCard(
                bagItem = bagItem,
                onDelete = { viewModel.removeFromBag(bagItem.product.id) }
            )
        }
    }
}

@Composable
fun RemovableBagItemCard(
    bagItem: com.example.myapplication.domain.models.BagItem,
    onDelete: () -> Unit
) {
    var isDeleteVisible by remember { mutableStateOf(false) }
    var cardHeightPx by remember { mutableIntStateOf(0) }

    BagItemCard(
        bagItem = bagItem,
        onDeleteClick = { isDeleteVisible = true },
        onDeleteConfirmClick = { onDelete() },
        onUndoClick = { isDeleteVisible = false },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (isDeleteVisible) {
                    onDelete()
                }
            },
        isDeleteVisible = isDeleteVisible,
        cardHeightPx = cardHeightPx,
        onCardHeightMeasured = { cardHeightPx = it }
    )
}

@Composable
fun BagItemCard(
    bagItem: com.example.myapplication.domain.models.BagItem,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit,
    onUndoClick: () -> Unit,
    isDeleteVisible: Boolean = false,
    cardHeightPx: Int = 0,
    onCardHeightMeasured: (Int) -> Unit = {}
) {
    val density = androidx.compose.ui.platform.LocalDensity.current
    Card(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
            onCardHeightMeasured(coordinates.size.height)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (cardHeightPx > 0) Modifier.height(with(density) { cardHeightPx.toDp() })
                    else Modifier
                )
        ) {
            if (isDeleteVisible) {
                // Delete overlay
                DeleteOverlay(
                    onUndoClick = onUndoClick,
                    onDeleteConfirmClick = onDeleteConfirmClick,
                )
            } else {
                // Normal content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = bagItem.product.image,
                            contentDescription = "${bagItem.product.title}. ${bagItem.product.description}",
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
                                text = bagItem.product.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Q-ty: ${bagItem.quantity}",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp, bottom = 16.dp),
                                text = "$${
                                    String.format(
                                        "%.2f",
                                        bagItem.product.price * bagItem.quantity
                                    )
                                }",
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "Delete",
                                color = Color.Red,
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .monitorElement(monitorTag = "delete_bag_item")
                                    .clickable { onDeleteClick() }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun DeleteOverlay(
    onUndoClick: () -> Unit,
    onDeleteConfirmClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .monitorElement(monitorTag = "undo_bag_item")
                    .fillMaxHeight()
                    .background(Color.Gray)
                    .padding(16.dp)
                    .clickable { onUndoClick() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Undo,
                    contentDescription = "Undo",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "Undo",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .monitorElement(monitorTag = "delete_confirm_bag_item")
                    .fillMaxHeight()
                    .background(Color.Red)
                    .padding(16.dp)
                    .clickable { onDeleteConfirmClick() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "Delete",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun BagSummaryCard(bag: com.example.myapplication.domain.models.Bag) {
    val totalItems = bag.items.sumOf { it.quantity }
    val totalPrice = bag.items.sumOf { it.product.price * it.quantity }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
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
            Text(
                text = "Items in Bag - $totalItems",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Total: $${String.format("%.2f", totalPrice)}",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSwipeableBagItemCard() {
    val mockBagItem = com.example.myapplication.domain.models.BagItem(
        product = com.example.myapplication.domain.models.Product(
            id = "1",
            title = "Sample Product",
            price = 29.99,
            description = "This is a sample product description",
            category = "electronics",
            image = "https://fakestoreapi.com/img/81Zt42ioCgL._AC_SX679_.jpg"
        ),
        quantity = 2
    )

    RemovableBagItemCard(
        bagItem = mockBagItem,
        onDelete = { /* Preview only */ }
    )
}

@Preview
@Composable
fun PreviewBagSummaryCard() {
    val mockBag = com.example.myapplication.domain.models.Bag(
        items = listOf(
            com.example.myapplication.domain.models.BagItem(
                product = com.example.myapplication.domain.models.Product(
                    id = "1",
                    title = "Product 1",
                    price = 29.99,
                    description = "Description 1",
                    category = "electronics",
                    image = "https://fakestoreapi.com/img/81Zt42ioCgL._AC_SX679_.jpg"
                ),
                quantity = 2
            ),
            com.example.myapplication.domain.models.BagItem(
                product = com.example.myapplication.domain.models.Product(
                    id = "2",
                    title = "Product 2",
                    price = 45.99,
                    description = "Description 2",
                    category = "jewelery",
                    image = "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg"
                ),
                quantity = 1
            )
        )
    )

    BagSummaryCard(bag = mockBag)
}

@Preview
@Composable
fun DeleteOverlayPreview() {
    DeleteOverlay({}, {})
}