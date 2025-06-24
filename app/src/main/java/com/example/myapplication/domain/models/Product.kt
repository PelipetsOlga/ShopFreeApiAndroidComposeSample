package com.example.myapplication.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating = RATING_EMPTY
)

@Serializable
data class Rating(
    val rate: Double,
    val count: Int
)

val RATING_EMPTY = Rating(rate = 0.0, count = 0)

val PRODUCT_EMPTY =
    Product(id = "", title = "", price = 0.0, description = "", category = "", image = "")

val RATING_MOCK = Rating(4.75, 124)
val PRODUCT_MOCK = Product(
    id = "163",
    title = "Jeans Skirt",
    price = 29.99,
    category = "Cloth for women",
    description = "Short bright jeans skirt is exactlu for you.",
    image = "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg",
    rating = RATING_MOCK
)