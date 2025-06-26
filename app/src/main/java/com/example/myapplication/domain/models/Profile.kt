package com.example.myapplication.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val personalData: PersonalData,
    val shippingAddress: ShippingAddress,
    val payments: Payments
)

@Serializable
data class PersonalData(
    val firstName: String,
    val secondName: String
)

@Serializable
data class ShippingAddress(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String
)

@Serializable
data class Payments(
    val creditCard: CreditCard
)

@Serializable
data class CreditCard(
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String
) 