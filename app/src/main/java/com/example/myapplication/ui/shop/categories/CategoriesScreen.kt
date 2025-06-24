package com.example.myapplication.ui.shop.categories

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.ui_components.CategoryCard

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit
) {
    val categories by viewModel.categories.collectAsState(emptyList())
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(categories.size) { index ->
            CategoryCard(
                categories[index],
                onCategoryClick = { onCategoryClick.invoke(categories[index].title) })
        }
    }
}