package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.domain.models.Profile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ProfilePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }

    fun saveProfile(profile: Profile) {
        val profileJson = json.encodeToString(profile)
        sharedPreferences.edit()
            .putString(KEY_PROFILE, profileJson)
            .apply()
    }

    fun loadProfile(): Profile? {
        val profileJson = sharedPreferences.getString(KEY_PROFILE, null)
        return if (profileJson != null) {
            try {
                json.decodeFromString<Profile>(profileJson)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    companion object {
        private const val PREF_NAME = "profile_preferences"
        private const val KEY_PROFILE = "profile"
    }
} 