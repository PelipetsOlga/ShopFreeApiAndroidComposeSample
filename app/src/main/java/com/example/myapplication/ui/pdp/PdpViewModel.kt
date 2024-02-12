package com.example.myapplication.ui.pdp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.Category
import com.example.myapplication.domain.models.PRODUCT_EMPTY
import com.example.myapplication.domain.models.Product
import com.example.myapplication.domain.usecases.GetProductUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PdpViewModel @Inject constructor(
    private val usecase: GetProductUsecase
) : ViewModel() {
    private val _product = MutableStateFlow(PRODUCT_EMPTY)
    val product: Flow<Product> get() = _product

    private fun fetchProduct(productId: Int) {
        viewModelScope.launch {
            try {
                usecase.execute(productId).collect {
                    _product.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _product.value = PRODUCT_EMPTY
            }
        }
    }
}