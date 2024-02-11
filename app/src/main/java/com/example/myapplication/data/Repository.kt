package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllCategories(): Flow<String>
}