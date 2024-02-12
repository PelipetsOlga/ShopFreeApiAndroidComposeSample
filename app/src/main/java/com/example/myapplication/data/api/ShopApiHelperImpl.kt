package com.example.myapplication.data.api

import com.example.myapplication.domain.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShopApiHelperImpl @Inject constructor(private val api: ShopApi) : ShopApiHelper {
    override fun getAllCategories(): Flow<List<String>> {
        return flow { emit(api.getAllCategories()) }
    }

    override fun getCategoryProducts(categoryName: String): Flow<List<Product>> {
        return flow { emit(api.getProductsInCategory(categoryName))  }
    }

    override fun getProductById(productId: Int): Flow<Product>{
        return flow { emit(api.getProductById(productId)) }
    }
}