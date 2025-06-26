package com.example.myapplication.ui.profile.personal_data

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.example.myapplication.domain.models.PersonalData
import com.example.myapplication.ui.profile.ProfileViewModel

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

        OutlinedTextField(
            value = firstName,
            onValueChange = { onFirstNameChange(it) },
            label = { Text("First Name") },
            modifier = Modifier
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

        Button(
            onClick = {
                val updatedPersonalData = PersonalData(
                    firstName = firstName.trim(),
                    secondName = secondName.trim()
                )
                viewModel.updatePersonalData(updatedPersonalData)
                onBackClick()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = firstNameError == null && secondNameError == null && 
                     firstName.trim().isNotBlank() && secondName.trim().isNotBlank()
        ) {
            Text("SAVE")
        }
    }
} 