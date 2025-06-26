package com.example.myapplication.ui.profile.personal_data

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
    var birthday by remember { mutableStateOf(personalData?.birthday ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Edit Personal Data",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = secondName,
            onValueChange = { secondName = it },
            label = { Text("Second Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text("Birthday (YYYY-MM-DD)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Button(
            onClick = {
                val updatedPersonalData = PersonalData(
                    firstName = firstName,
                    secondName = secondName,
                    birthday = birthday
                )
                profile?.let { currentProfile ->
                    val updatedProfile = currentProfile.copy(
                        personalData = updatedPersonalData
                    )
                    viewModel.saveProfile(updatedProfile)
                }
                onBackClick()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = firstName.isNotBlank() && secondName.isNotBlank() && birthday.isNotBlank()
        ) {
            Text("Save Changes")
        }
    }
} 