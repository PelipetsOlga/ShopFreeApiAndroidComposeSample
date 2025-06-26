package com.example.myapplication.domain

import com.example.myapplication.domain.models.Bag
import com.example.myapplication.domain.models.PersonalData
import com.example.myapplication.domain.models.Product
import com.example.myapplication.domain.models.Profile
import com.example.myapplication.domain.models.ShippingAddress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    fun getAllCategories(): Flow<List<String>>

    fun getCategoryProducts(categoryName: String): Flow<List<Product>>

    fun getProduct(productId: String): Flow<Product>

    fun addToBag(product: Product)

    fun addToBag(product: Product, qty: Int)

    fun removeFromBag(productId: String)

    fun getQuantityInBag(productId: String): Int

    fun updateQuantityInBag(productId: String, qty: Int)

    fun getBag(): StateFlow<Bag>

    fun saveProfile(profile: Profile)

    fun getProfile(): Profile?

    fun updatePersonalData(personalData: PersonalData)

    fun updateShippingAddress(shippingAddress: ShippingAddress)
}
