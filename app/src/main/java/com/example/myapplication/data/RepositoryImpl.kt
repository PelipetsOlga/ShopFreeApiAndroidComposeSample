package com.example.myapplication.data

import com.example.myapplication.data.api.ShopApiHelper
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiHelper: ShopApiHelper
) : Repository {
    override fun getAllCategories(): Flow<List<String>> {
        return apiHelper.getAllCategories()
    }

    override fun getCategoryProducts(categoryName: String): Flow<List<Product>> {
        return apiHelper.getCategoryProducts(categoryName)
    }

    override fun getProduct(productId: Int): Flow<Product>{
        return apiHelper.getProductById(productId)
    }
}