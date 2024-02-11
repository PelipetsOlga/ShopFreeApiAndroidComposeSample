package com.example.myapplication.data.usecases

import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Category
import com.example.myapplication.domain.usecases.GetCategoriesUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoriesUsecaseImpl @Inject constructor(
    private val repository: Repository
) : GetCategoriesUsecase {
    override fun execute(): Flow<List<Category>> {
        return repository.getAllCategories().map { list -> list.map { Category(it) } }
    }
}