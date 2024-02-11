package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface GetCategoryProductsUsecase {

    fun execute(categoryName: String): Flow<List<Product>>
}