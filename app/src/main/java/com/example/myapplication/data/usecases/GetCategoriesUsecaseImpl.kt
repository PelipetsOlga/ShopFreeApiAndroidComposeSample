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
        return repository.getAllCategories()
            .map { list ->
                list.map { it.toCategory() }
                    .flatMap { e -> listOf(e) }
            }
    }
}

private fun String.toCategory(): Category {
    return Category(title = this, image = this.toCategoryImage())
}

private fun String.toCategoryImage(): String {
    return when (this) {
        "electronics" -> "https://fakestoreapi.com/img/81Zt42ioCgL._AC_SX679_.jpg"
        "jewelery" -> "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg"
        "men's clothing" -> "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg"
        "women's clothing" -> "https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg"
        else -> "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg"
    }
}