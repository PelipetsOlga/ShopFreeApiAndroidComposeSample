package com.example.myapplication.data.usecases

import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Product
import com.example.myapplication.domain.usecases.GetCategoryProductsUsecase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryProductsUsecaseImpl @Inject constructor(
    private val repository: Repository
) : GetCategoryProductsUsecase {
    override fun execute(categoryName: String): Flow<List<Product>> {
        return repository.getCategoryProducts(categoryName)
    }
}