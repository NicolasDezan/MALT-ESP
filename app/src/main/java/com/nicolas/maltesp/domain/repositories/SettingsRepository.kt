package com.nicolas.maltesp.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isSystemInDarkTheme: Boolean
    suspend fun saveTheme(isDarkMode: Boolean)
    fun getTheme(): Flow<Boolean>
}