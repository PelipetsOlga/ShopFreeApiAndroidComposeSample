package com.example.myapplication.domain

import com.example.myapplication.domain.models.Bag
import com.example.myapplication.domain.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    fun getAllCategories(): Flow<List<String>>

    fun getCategoryProducts(categoryName: String): Flow<List<Product>>

    fun getProduct(productId: String): Flow<Product>

    fun addToBag(product: Product)

    fun removeFromBag(productId: String)

    fun getBag(): StateFlow<Bag>
}
