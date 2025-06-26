package com.example.myapplication.ui.profile.shipping_address

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.models.ShippingAddress
import com.example.myapplication.ui.profile.ProfileViewModel

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
    var country by remember { mutableStateOf(shippingAddress?.country ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Edit Shipping Address",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = street,
            onValueChange = { street = it },
            label = { Text("Street Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = state,
            onValueChange = { state = it },
            label = { Text("State/Province") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = zipCode,
            onValueChange = { zipCode = it },
            label = { Text("Zip/Postal Code") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Country") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true
        )

        Button(
            onClick = {
                val updatedShippingAddress = ShippingAddress(
                    street = street,
                    city = city,
                    state = state,
                    zipCode = zipCode,
                    country = country
                )
                profile?.let { currentProfile ->
                    val updatedProfile = currentProfile.copy(
                        shippingAddress = updatedShippingAddress
                    )
                    viewModel.saveProfile(updatedProfile)
                }
                onBackClick()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = street.isNotBlank() && city.isNotBlank() && state.isNotBlank() && 
                     zipCode.isNotBlank() && country.isNotBlank()
        ) {
            Text("Save Changes")
        }
    }
} 