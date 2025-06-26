package com.example.myapplication.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.models.CreditCard
import com.example.myapplication.domain.models.Payments
import com.example.myapplication.domain.models.PersonalData
import com.example.myapplication.domain.models.ShippingAddress
import com.example.myapplication.ui.profile.payment.CardMaskingUtils
import com.example.myapplication.ui.ui_components.AddIconButton
import com.example.myapplication.ui.ui_components.EditIconButton
import com.example.myapplication.ui.ui_components.RedPrimaryButton
import io.cux.analytics_sdk.composable.maskElement

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditPersonalData: () -> Unit = {},
    onEditShippingAddress: () -> Unit = {},
    onEditPaymentMethods: () -> Unit = {}
) {
    val profile by viewModel.profile.collectAsState()

    // Refresh profile data when screen becomes active
    LaunchedEffect(true) {
        viewModel.refreshProfileOnResume()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item { Spacer(Modifier.height(16.dp)) }

        item {
            PersonalDataCard(
                personalData = profile?.personalData, onEditClick = onEditPersonalData
            )
        }

        item {
            ShippingAddressCard(
                shippingAddress = profile?.shippingAddress, onEditClick = onEditShippingAddress
            )
        }

        item {
            PaymentsCard(
                payments = profile?.payments, onEditClick = onEditPaymentMethods
            )
        }

        // Delete Account Button
        if (profile?.personalData != null
            || profile?.shippingAddress != null
            || profile?.payments != null
        ) {
            item {
                RedPrimaryButton(
                    text = "DELETE ACCOUNT",
                    onClick = {
                        viewModel.deleteProfile()
                    },
                    modifier = Modifier.padding(top = 16.dp),
                    enabled = true
                )
            }
        }
        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
fun PersonalDataCard(
    personalData: PersonalData?, onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .maskElement()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Personal Data",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Personal Data",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (personalData == null) {
                    AddIconButton(
                        onClick = onEditClick,
                        contentDescription = "Add Personal Data"
                    )
                } else {
                    EditIconButton(
                        onClick = onEditClick,
                        contentDescription = "Edit Personal Data"
                    )
                }
            }

            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                if (personalData != null) {
                    ProfileField("First Name", personalData.firstName)
                    ProfileField("Second Name", personalData.secondName)
                } else {
                    Text(
                        text = "No Personal data yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun ShippingAddressCard(
    shippingAddress: ShippingAddress?,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .maskElement()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Shipping Address",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Shipping Address",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (shippingAddress == null) {
                    AddIconButton(
                        onClick = onEditClick,
                        contentDescription = "Add Shipping Address"
                    )
                } else {
                    EditIconButton(
                        onClick = onEditClick,
                        contentDescription = "Edit Shipping Address"
                    )
                }
            }

            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                if (shippingAddress != null) {
                    ProfileField("Street", shippingAddress.street)
                    ProfileField("City", shippingAddress.city)
                    ProfileField("State", shippingAddress.state)
                    ProfileField("Zip Code", shippingAddress.zipCode)
                    ProfileField("Country", shippingAddress.country)
                } else {
                    Text(
                        text = "No Shipping Address yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentsCard(
    payments: com.example.myapplication.domain.models.Payments?, onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .maskElement()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = "Payment Methods",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Payment",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (payments == null) {
                    AddIconButton(
                        onClick = onEditClick,
                        contentDescription = "Add Payment Method"
                    )
                } else {
                    EditIconButton(
                        onClick = onEditClick,
                        contentDescription = "Edit Payment Method"
                    )
                }
            }

            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                if (payments != null) {
                    CreditCardItem(creditCard = payments.creditCard, index = 1)
                } else {
                    Text(
                        text = "No Payment method yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun CreditCardItem(
    creditCard: com.example.myapplication.domain.models.CreditCard, index: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Card $index",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ProfileField("Card Number", CardMaskingUtils.maskCardNumber(creditCard.cardNumber))
        ProfileField("Expiry Date", creditCard.expiryDate)
        ProfileField("CVV", CardMaskingUtils.maskCVV(creditCard.cvv))
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value, style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            ), color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalDataCardWithDataPreview() {
    MaterialTheme {
        PersonalDataCard(personalData = PersonalData(
            firstName = "John", secondName = "Doe"
        ), onEditClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalDataCardNoDataPreview() {
    MaterialTheme {
        PersonalDataCard(personalData = null, onEditClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ShippingAddressCardWithDataPreview() {
    MaterialTheme {
        ShippingAddressCard(shippingAddress = ShippingAddress(
            street = "123 Main St",
            city = "New York",
            state = "NY",
            zipCode = "10001",
            country = "USA"
        ), onEditClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ShippingAddressCardNoDataPreview() {
    MaterialTheme {
        ShippingAddressCard(shippingAddress = null, onEditClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentsCardWithDataPreview() {
    MaterialTheme {
        PaymentsCard(payments = Payments(
            creditCard = CreditCard(
                cardNumber = "1234567890123456", expiryDate = "12/25", cvv = "123"
            )
        ), onEditClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentsCardNoDataPreview() {
    MaterialTheme {
        PaymentsCard(payments = null, onEditClick = {})
    }
} 