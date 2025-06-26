package com.example.myapplication.ui.profile.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.models.CreditCard
import com.example.myapplication.domain.models.Payments
import com.example.myapplication.ui.profile.ProfileViewModel

@Composable
fun EditPaymentMethodsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val profile by viewModel.profile.collectAsState()
    val initialCreditCard = profile?.payments?.creditCard
    
    var creditCard by remember { mutableStateOf(initialCreditCard) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Edit Payment",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (creditCard != null) {
            CreditCardEditSection(
                creditCard = creditCard!!,
                onUpdate = { updatedCard ->
                    creditCard = updatedCard
                },
                onDelete = {
                    creditCard = null
                },
                cardNumber = 1
            )
        } else {
            // Show empty state with option to add card
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No payment method added yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = {
                        creditCard = CreditCard(
                            cardNumber = "",
                            expiryDate = "",
                            cvv = ""
                        )
                    }
                ) {
                    Text("ADD PAYMENT METHOD")
                }
            }
        }

        if (creditCard != null) {
            Button(
                onClick = {
                    creditCard?.let { card ->
                        val updatedPayments = Payments(creditCard = card)
                        viewModel.updatePaymentMethods(updatedPayments)
                        onBackClick()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = creditCard!!.cardNumber.isNotBlank() && 
                         creditCard!!.expiryDate.isNotBlank() && 
                         creditCard!!.cvv.isNotBlank()
            ) {
                Text("SAVE")
            }
        }
    }
}

@Composable
fun CreditCardEditSection(
    creditCard: CreditCard,
    onUpdate: (CreditCard) -> Unit,
    onDelete: () -> Unit,
    cardNumber: Int
) {
    // State for input fields
    var cardNumberText by remember { mutableStateOf(creditCard.cardNumber) }
    var expiryDate by remember { mutableStateOf(creditCard.expiryDate) }
    var cvvText by remember { mutableStateOf("") } // Start with empty CVV

    // Validation states
    var cardNumberError by remember { mutableStateOf<String?>(null) }
    var expiryDateError by remember { mutableStateOf<String?>(null) }
    var cvvError by remember { mutableStateOf<String?>(null) }

    // Validation functions
    fun validateCardNumber(cardNumber: String): String? {
        val cleanNumber = CardMaskingUtils.cleanCardNumber(cardNumber)
        return when {
            cleanNumber.isBlank() -> "Card number is required"
            cleanNumber.length < 13 -> "Card number must be at least 13 digits"
            cleanNumber.length > 19 -> "Card number must be less than 19 digits"
            else -> null
        }
    }

    fun validateExpiryDate(expiryDate: String): String? {
        return when {
            expiryDate.isBlank() -> "Expiry date is required"
            !expiryDate.matches(Regex("^(0[1-9]|1[0-2])/([0-9]{2})$")) -> "Expiry date must be in MM/YY format"
            else -> null
        }
    }

    fun validateCVV(cvv: String): String? {
        return when {
            cvv.isBlank() -> "CVV is required"
            cvv.length < 3 -> "CVV must be at least 3 digits"
            cvv.length > 4 -> "CVV must be less than 4 digits"
            !cvv.matches(Regex("^[0-9]+$")) -> "CVV can only contain numbers"
            else -> null
        }
    }

    // Update validation on text change
    fun onCardNumberChange(newValue: String) {
        val cleanValue = CardMaskingUtils.cleanCardNumber(newValue)
        cardNumberText = cleanValue
        cardNumberError = validateCardNumber(cleanValue)
        onUpdate(creditCard.copy(cardNumber = cleanValue, cvv = cvvText))
    }

    fun onExpiryDateChange(newValue: String) {
        expiryDate = newValue
        expiryDateError = validateExpiryDate(newValue)
        onUpdate(creditCard.copy(expiryDate = newValue, cvv = cvvText))
    }

    fun onCVVChange(newValue: String) {
        val cleanValue = newValue.replace(Regex("[^0-9]"), "")
        cvvText = cleanValue
        cvvError = validateCVV(cleanValue)
        onUpdate(creditCard.copy(cvv = cleanValue))
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Card $cardNumber",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Card",
                    tint = Color.Red
                )
            }
        }

        OutlinedTextField(
            value = CardMaskingUtils.formatCardNumber(cardNumberText),
            onValueChange = { onCardNumberChange(it) },
            label = { Text("Card Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation.None,
            isError = cardNumberError != null,
            supportingText = {
                if (cardNumberError != null) {
                    Text(
                        text = cardNumberError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { onExpiryDateChange(it) },
                label = { Text("MM/YY") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = expiryDateError != null,
                supportingText = {
                    if (expiryDateError != null) {
                        Text(
                            text = expiryDateError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                value = CardMaskingUtils.maskCVV(cvvText),
                onValueChange = { onCVVChange(it) },
                label = { Text("CVV") },
                modifier = Modifier
                    .weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                visualTransformation = PasswordVisualTransformation(),
                isError = cvvError != null,
                supportingText = {
                    if (cvvError != null) {
                        Text(
                            text = cvvError!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    }
} 