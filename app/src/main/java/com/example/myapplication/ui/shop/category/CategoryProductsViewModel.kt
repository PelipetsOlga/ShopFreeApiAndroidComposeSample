package com.example.myapplication.ui.shop.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Product
import com.example.myapplication.domain.usecases.GetCategoryProductsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(
    private val usecase: GetCategoryProductsUsecase,
    private val repository: Repository
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: Flow<List<Product>> get() = _products

    fun fetchProducts(categoryName: String) {
        viewModelScope.launch {
            try {
                usecase.execute(categoryName).collect {
                    _products.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _products.value = emptyList()
            }
        }
    }

    fun addToBag(product: Product) {
        repository.addToBag(product)
    }
}