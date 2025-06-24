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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.domain.models.Category

@Composable
fun CategoryCard(category: Category, onCategoryClick: (String) -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onCategoryClick.invoke(category.title) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = category.image,
                contentDescription = category.title,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(category.title, fontWeight = FontWeight.ExtraBold)
            }

        }
    }
}

@Preview
@Composable
fun PreviewCategoryCard() {
    val mockCategory = Category(
        title = "Mock Category",
        image = "https://picsum.photos/200"
    )
    CategoryCard(category = mockCategory)
}