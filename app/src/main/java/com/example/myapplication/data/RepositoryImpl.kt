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
    private val apiHelper: ShopApiHelper,
    private val bagPreferences: BagPreferences
) : Repository {
    private val bag = MutableStateFlow(bagPreferences.loadBag())

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
            val newBag = currentBag.copy(
                items = currentBag.items.toMutableList()
                    .apply { add(BagItem(product = product, quantity = 1)) }.toList()
            )
            bag.value = newBag
            bagPreferences.saveBag(newBag)
        } else {
            val newBag = currentBag.copy(
                items = currentBag.items.map { bagItem ->
                    if (product.id == bagItem.product.id) {
                        bagItem.copy(quantity = bagItem.quantity + 1)
                    } else {
                        bagItem
                    }
                }
            )
            bag.value = newBag
            bagPreferences.saveBag(newBag)
        }
    }

    override fun addToBag(product: Product, qty: Int) {
        repeat(times = qty) {
            addToBag(product)
        }
    }

    override fun removeFromBag(productId: String) {
        val currentBag = bag.value
        val newBag = currentBag.copy(
            items = currentBag.items.filter { it.product.id != productId }
        )
        bag.value = newBag
        bagPreferences.saveBag(newBag)
    }

    override fun getQuantityInBag(productId: String): Int {
        return bag.value.items.filter { it.product.id == productId }.size
    }

    override fun updateQuantityInBag(productId: String, qty: Int) {
        val currentBag = bag.value
        val newBag = currentBag.copy(items = currentBag.items.map { bagItem ->
            if (productId == bagItem.product.id) {
                bagItem.copy(quantity = qty)
            } else {
                bagItem
            }
        })
        bag.value = newBag
        bagPreferences.saveBag(newBag)
    }

    override fun getBag(): StateFlow<Bag> = bag.asStateFlow()
}