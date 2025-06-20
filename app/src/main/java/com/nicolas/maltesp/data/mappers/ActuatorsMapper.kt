package com.nicolas.maltesp.data.mappers

import com.nicolas.maltesp.domain.models.ActuatorState

fun mapToActuatorState(convertedData: List<Int>): ActuatorState {
    return ActuatorState(
        entrada = convertedData[1] == 1,
        saida = convertedData[2] == 1,
        rotacao = convertedData[3] == 1,
        resistencia = convertedData[4] == 1,
        bombaAr = convertedData[5] == 1
    )
}