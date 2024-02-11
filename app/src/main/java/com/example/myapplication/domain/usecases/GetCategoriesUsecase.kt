package com.example.myapplication.domain.usecases

import com.example.myapplication.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface GetCategoriesUsecase {

    fun execute(): Flow<List<Category>>
}