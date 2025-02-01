package com.nicolas.maltesp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicolas.maltesp.data.MaltingRecipeDao

class ParametersViewModelFactory(
    private val dao: MaltingRecipeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParametersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ParametersViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}