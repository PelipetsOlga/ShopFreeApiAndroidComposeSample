package com.example.myapplication.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Bag(
    val items: List<BagItem>
)

@Serializable
data class BagItem(
    val product: Product,
    var quantity: Int,
)