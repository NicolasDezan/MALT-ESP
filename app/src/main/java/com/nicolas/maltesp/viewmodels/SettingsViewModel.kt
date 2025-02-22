package com.nicolas.maltesp.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.data.preferences.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel(){

    val themeFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.THEME] ?: false
    }

    fun saveTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.THEME] = isDarkMode
            }
        }
    }



    // Se um dia algo der merda, é culpa dessa função:
    fun getTheme(): Boolean {
        return runBlocking {
            dataStore.data.first()[PreferencesKeys.THEME] ?: false
        }
    }
}
