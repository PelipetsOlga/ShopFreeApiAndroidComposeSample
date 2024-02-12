package com.example.myapplication.ui.shop.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit
) {
    val categories by viewModel.categories.collectAsState(emptyList())
    Column {
        Text("Categories")
        LazyColumn {
            items(categories.size) { index ->
                Text(text = categories[index].title,
                    modifier = Modifier.clickable { onCategoryClick.invoke(categories[index].title) })
            }
        }
    }
}