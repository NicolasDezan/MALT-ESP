package com.nicolas.maltesp.domain.models

data class SensorReadData(
    val humidity: Float,
    val temperature: Float,
    val eco2: Int
)

data class SensorReadUiState(
    val temperature: String,
    val humidity: String,
    val eco2: String
)