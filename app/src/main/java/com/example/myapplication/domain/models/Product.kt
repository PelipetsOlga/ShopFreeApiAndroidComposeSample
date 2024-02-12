package com.example.myapplication.domain.models

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating = RATING_EMPTY
)

data class Rating(
    val rate: Double,
    val count: Int
)

val RATING_EMPTY = Rating(rate = 0.0, count = 0)

val PRODUCT_EMPTY =
    Product(id = 0, title = "", price = 0.0, description = "", category = "", image = "")