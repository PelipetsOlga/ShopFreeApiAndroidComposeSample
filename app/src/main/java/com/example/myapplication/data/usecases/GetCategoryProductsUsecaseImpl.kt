package com.example.myapplication.data.usecases

import com.example.myapplication.domain.models.Product
import com.example.myapplication.domain.usecases.GetCategoryProductsUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoryProductsUsecaseImpl : GetCategoryProductsUsecase {
    override fun execute(categoryName: String): Flow<List<Product>>{
        return flow { emit(emptyList()) }
    }
}