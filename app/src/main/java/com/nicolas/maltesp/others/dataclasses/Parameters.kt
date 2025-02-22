package com.nicolas.maltesp.others.dataclasses

import androidx.compose.runtime.MutableState

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

data class ParameterData(
    val name: String,
    val unit: String,
    val description: String,
    val state: MutableState<String>,
    val range: ParametersRange,
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