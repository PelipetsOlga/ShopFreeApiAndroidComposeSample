package com.example.myapplication.domain

import com.example.myapplication.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllCategories(): Flow<List<String>>

    fun getCategoryProducts(categoryName: String): Flow<List<Product>>
}
