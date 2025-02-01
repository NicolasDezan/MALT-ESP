package com.nicolas.maltesp.data.dataclasses

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Data classes para armazenar os valores dos parâmetros
data class ParametersState(
    val steeping: SteepingState,
    val germination: GerminationState,
    val kilning: KilningState
)

data class SteepingState(
    val submergedTime: MutableState<String>,
    val waterVolume: MutableState<String>,
    val restTime: MutableState<String>,
    val cycles: MutableState<String>
)

data class GerminationState(
    val rotationLevel: MutableState<String>,
    val totalTime: MutableState<String>,
    val waterVolume: MutableState<String>,
    val waterAddition: MutableState<String>
)

data class KilningState(
    val temperature: MutableState<String>,
    val time: MutableState<String>
)

// Função para inicializar os estados
fun initializeParametersState(): ParametersState {
    return ParametersState(
        steeping = SteepingState(
            submergedTime = mutableStateOf(""),
            waterVolume = mutableStateOf(""),
            restTime = mutableStateOf(""),
            cycles = mutableStateOf("")
        ),
        germination = GerminationState(
            rotationLevel = mutableStateOf(""),
            totalTime = mutableStateOf(""),
            waterVolume = mutableStateOf(""),
            waterAddition = mutableStateOf("")
        ),
        kilning = KilningState(
            temperature = mutableStateOf(""),
            time = mutableStateOf("")
        )
    )
}