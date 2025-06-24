package com.example.myapplication.ui.search

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.Bag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    
    val bag: StateFlow<Bag> = repository.getBag()
} 