package com.nicolas.maltesp.domain.models

data class ParametersRange(
    val min: Float,
    val max: Float
)

data class ParametersRangeGroup(
    val steeping: SteepingParametersRange,
    val germination: GerminationParametersRange,
    val kilning: KilningParametersRange
)

data class SteepingParametersRange(
    val submergedTime: ParametersRange,
    val waterVolume: ParametersRange,
    val restTime: ParametersRange,
    val cycles: ParametersRange
)

data class GerminationParametersRange(
    val rotationLevel: ParametersRange,
    val totalTime: ParametersRange,
    val waterVolume: ParametersRange,
    val waterAddition: ParametersRange
)

data class KilningParametersRange(
    val temperature: ParametersRange,
    val time: ParametersRange
)

