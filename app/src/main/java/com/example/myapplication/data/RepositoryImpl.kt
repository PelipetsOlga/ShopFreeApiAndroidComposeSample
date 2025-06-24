package com.example.myapplication.data

import com.example.myapplication.data.api.ShopApiHelper
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Bag
import com.example.myapplication.domain.models.BagItem
import com.example.myapplication.domain.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiHelper: ShopApiHelper
) : Repository {
    private val bag = MutableStateFlow(Bag(items = emptyList()))

    override fun getAllCategories(): Flow<List<String>> {
        return apiHelper.getAllCategories()
    }

    override fun getCategoryProducts(categoryName: String): Flow<List<Product>> {
        return apiHelper.getCategoryProducts(categoryName)
    }

    override fun getProduct(productId: String): Flow<Product> {
        return apiHelper.getProductById(productId)
    }

    override fun addToBag(product: Product) {
        val currentBag = bag.value
        val bagItem = currentBag.items.firstOrNull { it.product.id == product.id }
        if (bagItem == null) {
            bag.value = currentBag.copy(
                items = currentBag.items.toMutableList()
                    .apply { add(BagItem(product = product, quantity = 1)) }.toList()
            )
        } else {
            bag.value = currentBag.copy(
                items = currentBag.items.map { bagItem ->
                    if (product.id == bagItem.product.id) {
                        bagItem.copy(quantity = bagItem.quantity + 1)
                    } else {
                        bagItem
                    }
                }
            )
        }
    }

    override fun removeFromBag(productId: String) {
        val currentBag = bag.value
        bag.value = currentBag.copy(
            items = currentBag.items.filter { it.product.id != productId }
        )
    }

    override fun getBag(): StateFlow<Bag> = bag.asStateFlow()
}