package com.example.myapplication.domain.models

data class Bag(
    val items: List<BagItem>
)

data class BagItem(
    val product: Product,
    var quantity: Int,
)