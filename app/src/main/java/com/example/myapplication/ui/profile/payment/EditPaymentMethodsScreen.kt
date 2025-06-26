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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
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
    val initialCreditCards = profile?.payments?.creditCards ?: emptyList()
    
    val creditCards = remember { mutableStateListOf<CreditCard>().apply { addAll(initialCreditCards) } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Edit Payment Methods",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        creditCards.forEachIndexed { index, creditCard ->
            CreditCardEditCard(
                creditCard = creditCard,
                onUpdate = { updatedCard ->
                    creditCards[index] = updatedCard
                },
                onDelete = {
                    creditCards.removeAt(index)
                },
                cardNumber = index + 1
            )
            
            if (index < creditCards.size - 1) {
                androidx.compose.material3.HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }

        Button(
            onClick = {
                val newCard = CreditCard(
                    cardNumber = "",
                    cardHolderName = "",
                    expiryDate = "",
                    cvv = ""
                )
                creditCards.add(newCard)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Card",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Add New Card")
        }

        Button(
            onClick = {
                val updatedPayments = Payments(creditCards = creditCards.toList())
                profile?.let { currentProfile ->
                    val updatedProfile = currentProfile.copy(
                        payments = updatedPayments
                    )
                    viewModel.saveProfile(updatedProfile)
                }
                onBackClick()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = creditCards.isNotEmpty() && creditCards.all { card ->
                card.cardNumber.isNotBlank() && card.cardHolderName.isNotBlank() && 
                card.expiryDate.isNotBlank() && card.cvv.isNotBlank()
            }
        ) {
            Text("Save Changes")
        }
    }
}

@Composable
fun CreditCardEditCard(
    creditCard: CreditCard,
    onUpdate: (CreditCard) -> Unit,
    onDelete: () -> Unit,
    cardNumber: Int
) {
    var cardNumberText by remember { mutableStateOf(creditCard.cardNumber) }
    var cardHolderName by remember { mutableStateOf(creditCard.cardHolderName) }
    var expiryDate by remember { mutableStateOf(creditCard.expiryDate) }
    var cvv by remember { mutableStateOf(creditCard.cvv) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Card $cardNumber",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
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
                value = cardNumberText,
                onValueChange = { 
                    cardNumberText = it
                    onUpdate(creditCard.copy(cardNumber = it))
                },
                label = { Text("Card Number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = cardHolderName,
                onValueChange = { 
                    cardHolderName = it
                    onUpdate(creditCard.copy(cardHolderName = it))
                },
                label = { Text("Card Holder Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { 
                        expiryDate = it
                        onUpdate(creditCard.copy(expiryDate = it))
                    },
                    label = { Text("MM/YY") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { 
                        cvv = it
                        onUpdate(creditCard.copy(cvv = it))
                    },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
            }
        }
    }
} 