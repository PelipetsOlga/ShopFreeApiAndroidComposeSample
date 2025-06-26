package com.example.myapplication.ui.profile.shipping_address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.models.ShippingAddress
import com.example.myapplication.ui.profile.ProfileViewModel
import com.example.myapplication.ui.ui_components.DeleteIconButton
import com.example.myapplication.ui.ui_components.PrimaryButton
import io.cux.analytics_sdk.composable.maskElement

@Composable
fun EditShippingAddressScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val profile by viewModel.profile.collectAsState()
    val shippingAddress = profile?.shippingAddress

    var street by remember { mutableStateOf(shippingAddress?.street ?: "") }
    var city by remember { mutableStateOf(shippingAddress?.city ?: "") }
    var state by remember { mutableStateOf(shippingAddress?.state ?: "") }
    var zipCode by remember { mutableStateOf(shippingAddress?.zipCode ?: "") }
    val country = "USA" // Fixed value

    // Validation states
    var streetError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var stateError by remember { mutableStateOf<String?>(null) }
    var zipCodeError by remember { mutableStateOf<String?>(null) }

    // Validation functions
    fun validateStreet(street: String): String? {
        return when {
            street.isBlank() -> "Street address is required"
            street.length < 5 -> "Street address must be at least 5 characters"
            street.length > 100 -> "Street address must be less than 100 characters"
            !street.matches(Regex("^[a-zA-Z0-9\\s\\-\\#\\.]+$")) -> "Street address can only contain letters, numbers, spaces, hyphens, and periods"
            else -> null
        }
    }

    fun validateCity(city: String): String? {
        return when {
            city.isBlank() -> "City is required"
            city.length < 2 -> "City must be at least 2 characters"
            city.length > 50 -> "City must be less than 50 characters"
            !city.matches(Regex("^[a-zA-Z\\s\\-']+$")) -> "City can only contain letters, spaces, hyphens, and apostrophes"
            else -> null
        }
    }

    fun validateState(state: String): String? {
        return when {
            state.isBlank() -> "State is required"
            state.length < 2 -> "State must be at least 2 characters"
            state.length > 30 -> "State must be less than 30 characters"
            !state.matches(Regex("^[a-zA-Z\\s]+$")) -> "State can only contain letters and spaces"
            else -> null
        }
    }

    fun validateZipCode(zipCode: String): String? {
        return when {
            zipCode.isBlank() -> "Zip code is required"
            zipCode.length < 5 -> "Zip code must be at least 5 digits"
            zipCode.length > 10 -> "Zip code must be less than 10 characters"
            !zipCode.matches(Regex("^[0-9\\-]+$")) -> "Zip code can only contain numbers and hyphens"
            else -> null
        }
    }

    // Update validation on text change
    fun onStreetChange(newValue: String) {
        street = newValue
        streetError = validateStreet(newValue)
    }

    fun onCityChange(newValue: String) {
        city = newValue
        cityError = validateCity(newValue)
    }

    fun onStateChange(newValue: String) {
        state = newValue
        stateError = validateState(newValue)
    }

    fun onZipCodeChange(newValue: String) {
        zipCode = newValue
        zipCodeError = validateZipCode(newValue)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (shippingAddress != null) {
                DeleteIconButton(
                    onClick = {
                        viewModel.deleteShippingAddress()
                        onBackClick()
                    }
                )
            }
        }

        OutlinedTextField(
            value = street,
            onValueChange = { onStreetChange(it) },
            label = { Text("Street Address") },
            modifier = Modifier
                .fillMaxWidth()
                .maskElement()
                .padding(bottom = 16.dp),
            singleLine = true,
            isError = streetError != null,
            supportingText = {
                if (streetError != null) {
                    Text(
                        text = streetError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        OutlinedTextField(
            value = city,
            onValueChange = { onCityChange(it) },
            label = { Text("City") },
            modifier = Modifier
                .fillMaxWidth()
                .maskElement()
                .padding(bottom = 16.dp),
            singleLine = true,
            isError = cityError != null,
            supportingText = {
                if (cityError != null) {
                    Text(
                        text = cityError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        OutlinedTextField(
            value = state,
            onValueChange = { onStateChange(it) },
            label = { Text("State/Province") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            isError = stateError != null,
            supportingText = {
                if (stateError != null) {
                    Text(
                        text = stateError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        OutlinedTextField(
            value = zipCode,
            onValueChange = { onZipCodeChange(it) },
            label = { Text("Zip/Postal Code") },
            modifier = Modifier
                .fillMaxWidth()
                .maskElement()
                .padding(bottom = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = zipCodeError != null,
            supportingText = {
                if (zipCodeError != null) {
                    Text(
                        text = zipCodeError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        OutlinedTextField(
            value = country,
            onValueChange = { /* Read-only */ },
            label = { Text("Country") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true,
            enabled = false
        )

        PrimaryButton(
            text = "SAVE",
            onClick = {
                val updatedShippingAddress = ShippingAddress(
                    street = street.trim(),
                    city = city.trim(),
                    state = state.trim(),
                    zipCode = zipCode.trim(),
                    country = country
                )
                viewModel.updateShippingAddress(updatedShippingAddress)
                onBackClick()
            },
            enabled = streetError == null && cityError == null && stateError == null &&
                    zipCodeError == null && street.trim().isNotBlank() && city.trim()
                .isNotBlank() &&
                    state.trim().isNotBlank() && zipCode.trim().isNotBlank()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditShippingAddressScreenPreview() {
    MaterialTheme {
        EditShippingAddressScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ShippingAddressNoDataPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No Shipping Address yet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
} 