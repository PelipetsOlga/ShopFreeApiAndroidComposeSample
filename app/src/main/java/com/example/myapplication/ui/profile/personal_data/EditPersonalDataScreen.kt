package com.example.myapplication.ui.profile.personal_data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.models.PersonalData
import com.example.myapplication.ui.profile.ProfileViewModel
import com.example.myapplication.ui.ui_components.DeleteIconButton
import com.example.myapplication.ui.ui_components.PrimaryButton
import io.cux.analytics_sdk.composable.maskElement

@Composable
fun EditPersonalDataScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val profile by viewModel.profile.collectAsState()
    val personalData = profile?.personalData

    var firstName by remember { mutableStateOf(personalData?.firstName ?: "") }
    var secondName by remember { mutableStateOf(personalData?.secondName ?: "") }

    // Validation states
    var firstNameError by remember { mutableStateOf<String?>(null) }
    var secondNameError by remember { mutableStateOf<String?>(null) }

    // Validation functions
    fun validateFirstName(name: String): String? {
        return when {
            name.isBlank() -> "First name is required"
            name.length < 2 -> "First name must be at least 2 characters"
            name.length > 50 -> "First name must be less than 50 characters"
            !name.matches(Regex("^[a-zA-Z\\s'-]+$")) -> "First name can only contain letters, spaces, hyphens, and apostrophes"
            else -> null
        }
    }

    fun validateSecondName(name: String): String? {
        return when {
            name.isBlank() -> "Second name is required"
            name.length < 2 -> "Second name must be at least 2 characters"
            name.length > 50 -> "Second name must be less than 50 characters"
            !name.matches(Regex("^[a-zA-Z\\s'-]+$")) -> "Second name can only contain letters, spaces, hyphens, and apostrophes"
            else -> null
        }
    }

    // Update validation on text change
    fun onFirstNameChange(newValue: String) {
        firstName = newValue
        firstNameError = validateFirstName(newValue)
    }

    fun onSecondNameChange(newValue: String) {
        secondName = newValue
        secondNameError = validateSecondName(newValue)
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
            Text(
                text = "Edit Personal Data",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            if (personalData != null) {
                DeleteIconButton(
                    onClick = {
                        viewModel.deletePersonalData()
                        onBackClick()
                    }
                )
            }
        }

        OutlinedTextField(
            value = firstName,
            onValueChange = { onFirstNameChange(it) },
            label = { Text("First Name") },
            modifier = Modifier
                .maskElement()
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            isError = firstNameError != null,
            supportingText = {
                if (firstNameError != null) {
                    Text(
                        text = firstNameError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        OutlinedTextField(
            value = secondName,
            onValueChange = { onSecondNameChange(it) },
            label = { Text("Second Name") },
            modifier = Modifier
                .fillMaxWidth()
                .maskElement()
                .padding(bottom = 24.dp),
            singleLine = true,
            isError = secondNameError != null,
            supportingText = {
                if (secondNameError != null) {
                    Text(
                        text = secondNameError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        PrimaryButton(
            text = "SAVE",
            onClick = {
                val updatedPersonalData = PersonalData(
                    firstName = firstName.trim(),
                    secondName = secondName.trim()
                )
                viewModel.updatePersonalData(updatedPersonalData)
                onBackClick()
            },
            enabled = firstNameError == null && secondNameError == null &&
                    firstName.trim().isNotBlank() && secondName.trim().isNotBlank()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditPersonalDataScreenPreview() {
    MaterialTheme {
        EditPersonalDataScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalDataNoDataPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No Personal data yet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
} 