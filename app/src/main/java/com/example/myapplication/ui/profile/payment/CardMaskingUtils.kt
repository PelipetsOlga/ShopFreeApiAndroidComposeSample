package com.example.myapplication.ui.profile.payment

object CardMaskingUtils {
    
    /**
     * Masks a card number for display, showing only the last 3 digits
     * Example: "1234567890123456" -> "************3456"
     */
    fun maskCardNumber(cardNumber: String): String {
        if (cardNumber.isBlank()) return ""
        
        val cleanNumber = cardNumber.replace(Regex("[^0-9]"), "")
        if (cleanNumber.length < 4) return "*".repeat(cleanNumber.length)
        
        val lastFour = cleanNumber.takeLast(3)
        val maskedPart = "*".repeat(cleanNumber.length - 3)
        return maskedPart + lastFour
    }
    
    /**
     * Masks CVV for display, showing only asterisks
     */
    fun maskCVV(cvv: String): String {
        return "*".repeat(cvv.length.coerceAtLeast(3))
    }
    
    /**
     * Cleans a card number by removing non-digit characters
     */
    fun cleanCardNumber(cardNumber: String): String {
        return cardNumber.replace(Regex("[^0-9]"), "")
    }
    
    /**
     * Formats a card number with spaces for better readability
     * Example: "1234567890123456" -> "1234 5678 9012 3456"
     */
    fun formatCardNumber(cardNumber: String): String {
        val cleanNumber = cleanCardNumber(cardNumber)
        return cleanNumber.chunked(4).joinToString(" ")
    }
} 