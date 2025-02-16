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

// FunçãoTroll para forçar valores duvidosos
fun testParametersState(): ParametersState {
    return ParametersState( // TODO: configurar o ESP32 para enviar os parametros corretamente
        steeping = SteepingState(
            submergedTime = mutableStateOf(10.toString()),
            waterVolume = mutableStateOf(11.toString()),
            restTime = mutableStateOf(12.toString()),
            cycles = mutableStateOf(13.toString())
        ),
        germination = GerminationState(
            rotationLevel = mutableStateOf(14.toString()),
            totalTime = mutableStateOf(15.toString()),
            waterVolume = mutableStateOf(16.toString()),
            waterAddition = mutableStateOf(17.toString())
        ),
        kilning = KilningState(
            temperature = mutableStateOf(18.toString()),
            time = mutableStateOf(19.toString())
        )
    )
}

data class ParameterData(
    val name: String,
    val unit: String,
    val description: String,
    val state: MutableState<String>,
    val isEquals: Boolean?, // Se nulo, significa que há problema na conexão
    val isNumberValid: Boolean = false
)

data class ParameterGroup(
    val title: String? = null,
    val parameters: List<ParameterData>
)

data class ParameterSectionData(
    val title: String,
    val groups: List<ParameterGroup>
)

data class ParametersRange(
    val min: Float,
    val max: Float
)