package com.example.myapplication.data.usecases

import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Product
import com.example.myapplication.domain.usecases.GetProductUsecase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUsecaseImpl @Inject constructor(
    private val repository: Repository
) : GetProductUsecase {
    override fun execute(productId: String): Flow<Product> {
        return repository.getProduct(productId)
    }
}