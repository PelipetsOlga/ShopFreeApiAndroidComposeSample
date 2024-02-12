package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface GetProductUsecase {

    fun execute(productId: Int): Flow<Product>
}