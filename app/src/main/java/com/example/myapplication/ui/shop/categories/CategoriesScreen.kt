package com.example.myapplication.ui.shop.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.ui_components.CategoryCard

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit
) {
    val categories by viewModel.categories.collectAsState(emptyList())
    Column {
        LazyColumn {
            items(categories.size) { index ->
                CategoryCard(
                    categories[index],
                    onCategoryClick = { onCategoryClick.invoke(categories[index].title) })
            }
        }
    }
}