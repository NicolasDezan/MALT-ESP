package com.nicolas.maltesp.domain.models

data class ActuatorState(
    val entrada: Boolean,
    val saida: Boolean,
    val rotacao: Boolean,
    val resistencia: Boolean,
    val bombaAr: Boolean
)

data class ActuatorUiState(
    val entrada: String,
    val saida: String,
    val rotacao: String,
    val resistencia: String,
    val bombaAr: String
)