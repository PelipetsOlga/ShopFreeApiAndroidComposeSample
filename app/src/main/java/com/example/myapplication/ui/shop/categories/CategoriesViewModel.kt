package com.example.myapplication.ui.shop.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.Category
import com.example.myapplication.domain.usecases.GetCategoriesUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val usecase: GetCategoriesUsecase
) : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: Flow<List<Category>> get() = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                usecase.execute().collect {
                    _categories.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _categories.value = emptyList()
            }
        }
    }

}