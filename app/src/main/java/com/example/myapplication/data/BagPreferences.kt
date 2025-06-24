package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.domain.models.Bag
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BagPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        BAG_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
    
    private val json = Json { 
        ignoreUnknownKeys = true 
        isLenient = true
    }
    
    fun saveBag(bag: Bag) {
        val bagJson = json.encodeToString(Bag.serializer(), bag)
        sharedPreferences.edit()
            .putString(KEY_BAG_DATA, bagJson)
            .apply()
    }
    
    fun loadBag(): Bag {
        val bagJson = sharedPreferences.getString(KEY_BAG_DATA, null)
        return if (bagJson != null) {
            try {
                json.decodeFromString(Bag.serializer(), bagJson)
            } catch (e: Exception) {
                e.printStackTrace()
                Bag(items = emptyList())
            }
        } else {
            Bag(items = emptyList())
        }
    }
    
    fun clearBag() {
        sharedPreferences.edit()
            .remove(KEY_BAG_DATA)
            .apply()
    }
    
    companion object {
        private const val BAG_PREFERENCES_NAME = "bag_preferences"
        private const val KEY_BAG_DATA = "bag_data"
    }
} 