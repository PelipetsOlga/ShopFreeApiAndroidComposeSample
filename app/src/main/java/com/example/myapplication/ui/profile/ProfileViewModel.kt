package com.example.myapplication.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Profile
import com.example.myapplication.domain.models.PersonalData
import com.example.myapplication.domain.models.ShippingAddress
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

    fun loadProfile() {
        viewModelScope.launch {
            _profile.value = repository.getProfile()
        }
    }

    fun refreshProfile() {
        loadProfile()
    }

    fun saveProfile(profile: Profile) {
        viewModelScope.launch {
            repository.saveProfile(profile)
            _profile.value = profile
        }
    }

    fun updatePersonalData(personalData: PersonalData) {
        viewModelScope.launch {
            repository.updatePersonalData(personalData)
            // Update the local state immediately with the new personal data
            _profile.value?.let { currentProfile ->
                _profile.value = currentProfile.copy(personalData = personalData)
            }
        }
    }

    fun updateShippingAddress(shippingAddress: ShippingAddress) {
        viewModelScope.launch {
            repository.updateShippingAddress(shippingAddress)
            // Update the local state immediately with the new shipping address
            _profile.value?.let { currentProfile ->
                _profile.value = currentProfile.copy(shippingAddress = shippingAddress)
            }
        }
    }

    fun refreshProfileOnResume() {
        viewModelScope.launch {
            _profile.value = repository.getProfile()
        }
    }

    fun createDefaultProfile(): Profile {
        return Profile(
            personalData = com.example.myapplication.domain.models.PersonalData(
                firstName = "John",
                secondName = "Doe"
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
                        expiryDate = "12/25"
                    )
                )
            )
        )
    }
} 