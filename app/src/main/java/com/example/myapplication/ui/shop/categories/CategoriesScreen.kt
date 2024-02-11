package com.example.myapplication.ui.shop.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
) {
    val categories by viewModel.categories.collectAsState(emptyList())
    Column {
        Text("Categories")
        LazyColumn{
            items(categories.size) { index ->
                Text(text = categories[index].title)
            }
        }
    }
    
}