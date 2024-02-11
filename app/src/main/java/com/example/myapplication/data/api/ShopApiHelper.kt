package com.example.myapplication.data.api

import com.example.myapplication.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ShopApiHelper {
    fun getAllCategories(): Flow<List<String>>

    fun getCategoryProducts(categoryName: String): Flow<List<Product>>
}