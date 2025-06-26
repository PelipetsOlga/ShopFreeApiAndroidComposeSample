package com.example.myapplication.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val personalData: PersonalData?, val shippingAddress: ShippingAddress?, val payments: Payments?
) {
    companion object {
        val EMPTY = Profile(null, null, null)
        val DEFAULT = Profile(
            personalData = PersonalData(
                firstName = "John", secondName = "Doe"
            ), shippingAddress = ShippingAddress(
                street = "123 Main St",
                city = "New York",
                state = "NY",
                zipCode = "10001",
                country = "USA"
            ), payments = Payments(
                creditCard = CreditCard(
                    cardNumber = "1234567890123456", expiryDate = "12/25", cvv = "123"
                )
            )
        )
    }
}

@Serializable
data class PersonalData(
    val firstName: String, val secondName: String
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
    val cardNumber: String, val expiryDate: String, val cvv: String
) 