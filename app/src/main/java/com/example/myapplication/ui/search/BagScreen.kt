package com.example.myapplication.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BagScreen(
    modifier: Modifier = Modifier,
    viewModel: BagViewModel = hiltViewModel()
) {
    val bag by viewModel.bag.collectAsState()
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(bag.items) { bagItem ->
                Text(
                    text = "${bagItem.product.title} (Qty: ${bagItem.quantity})",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}