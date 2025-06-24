package com.example.myapplication.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _profile.value = repository.getProfile()
        }
    }

    fun saveProfile(profile: Profile) {
        viewModelScope.launch {
            repository.saveProfile(profile)
            _profile.value = profile
        }
    }

    fun createDefaultProfile(): Profile {
        return Profile(
            personalData = com.example.myapplication.domain.models.PersonalData(
                firstName = "John",
                secondName = "Doe",
                birthday = "1990-01-01"
            ),
            shippingAddress = com.example.myapplication.domain.models.ShippingAddress(
                street = "123 Main St",
                city = "New York",
                state = "NY",
                zipCode = "10001",
                country = "USA"
            ),
            payments = com.example.myapplication.domain.models.Payments(
                creditCards = listOf(
                    com.example.myapplication.domain.models.CreditCard(
                        cardNumber = "**** **** **** 1234",
                        cardHolderName = "John Doe",
                        expiryDate = "12/25",
                        cvv = "123"
                    )
                )
            )
        )
    }
} 