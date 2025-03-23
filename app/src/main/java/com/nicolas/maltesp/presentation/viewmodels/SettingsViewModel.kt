package com.nicolas.maltesp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.domain.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel(){

    val themeFlow: StateFlow<Boolean> = settingsRepository.getTheme()
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun saveTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveTheme(isDarkMode)
        }
    }
}



