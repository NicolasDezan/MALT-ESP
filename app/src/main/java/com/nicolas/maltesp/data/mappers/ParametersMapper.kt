package com.nicolas.maltesp.data.mappers

import androidx.compose.runtime.mutableStateOf
import com.nicolas.maltesp.domain.constants.MinRangeValues
import com.nicolas.maltesp.domain.constants.MultiplierRange
import com.nicolas.maltesp.domain.models.GerminationState
import com.nicolas.maltesp.domain.models.KilningState
import com.nicolas.maltesp.domain.models.ParametersState
import com.nicolas.maltesp.domain.models.SteepingState


fun receiveParameters(convertedData: List<Int>): ParametersState {
    return ParametersState(
        steeping = SteepingState(
            submergedTime = mutableStateOf(
                (convertedData[1] * MultiplierRange.Steeping.SUBMERGED_TIME + MinRangeValues.Steeping.SUBMERGED_TIME)
                    .toString()
            ),
            waterVolume = mutableStateOf(
                (convertedData[2] * MultiplierRange.Steeping.WATER_VOLUME + MinRangeValues.Steeping.WATER_VOLUME)
                    .toString()
            ),
            restTime = mutableStateOf(
                (convertedData[3] * MultiplierRange.Steeping.REST_TIME + MinRangeValues.Steeping.REST_TIME)
                    .toString()
            ),
            cycles = mutableStateOf(
                (convertedData[4] * MultiplierRange.Steeping.CYCLES + MinRangeValues.Steeping.CYCLES)
                    .toString()
            )
        ),
        germination = GerminationState(
            rotationLevel = mutableStateOf(
                (convertedData[5] * MultiplierRange.Germination.ROTATION_LEVEL + MinRangeValues.Germination.ROTATION_LEVEL)
                    .toString()
            ),
            totalTime = mutableStateOf(
                (convertedData[6] * MultiplierRange.Germination.TOTAL_TIME + MinRangeValues.Germination.TOTAL_TIME)
                    .toString()
            ),
            waterVolume = mutableStateOf(
                (convertedData[7] * MultiplierRange.Germination.WATER_VOLUME + MinRangeValues.Germination.WATER_VOLUME)
                    .toString()
            ),
            waterAddition = mutableStateOf(
                (convertedData[8] * MultiplierRange.Germination.WATER_ADDITION + MinRangeValues.Germination.WATER_ADDITION)
                    .toString()
            )
        ),
        kilning = KilningState(
            temperature = mutableStateOf(
                (convertedData[9] * MultiplierRange.Kilning.TEMPERATURE + MinRangeValues.Kilning.TEMPERATURE)
                    .toString()
            ),
            time = mutableStateOf(
                (convertedData[10] * MultiplierRange.Kilning.TIME + MinRangeValues.Kilning.TIME)
                    .toString()
            )
        )
    )
}