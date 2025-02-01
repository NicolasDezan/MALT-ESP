package com.nicolas.maltesp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class BottomBarViewModel : ViewModel() {
    private val _selectedItem = MutableStateFlow(0)
    val selectedItem = _selectedItem.asStateFlow()

    fun selectItem(index: Int) {
        _selectedItem.value = index
    }
}