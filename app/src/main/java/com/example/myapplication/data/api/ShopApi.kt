package com.example.myapplication.data.api

import com.example.myapplication.domain.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopApi {

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("/products/category/{categoryName}")
    suspend fun getProductsInCategory(
        @Path("categoryName") categoryName: String
    ): List<Product>
}