package com.nicolas.maltesp.others.classes

import androidx.compose.runtime.mutableStateOf
import com.nicolas.maltesp.others.dataclasses.GerminationParametersRange
import com.nicolas.maltesp.others.dataclasses.GerminationState
import com.nicolas.maltesp.others.dataclasses.KilningParametersRange
import com.nicolas.maltesp.others.dataclasses.KilningState
import com.nicolas.maltesp.others.dataclasses.ParametersRange
import com.nicolas.maltesp.others.dataclasses.ParametersRangeGroup
import com.nicolas.maltesp.others.dataclasses.ParametersState
import com.nicolas.maltesp.others.dataclasses.SteepingParametersRange
import com.nicolas.maltesp.others.dataclasses.SteepingState
import com.nicolas.maltesp.others.objects.MinRangeValues
import com.nicolas.maltesp.others.objects.MultiplierRange

class Parameters {
    companion object {
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

        fun initializeRangeGroup(): ParametersRangeGroup {
            return ParametersRangeGroup(
                steeping = SteepingParametersRange(

                    submergedTime = ParametersRange(
                        min = MinRangeValues.Steeping.SUBMERGED_TIME,
                        max = calculateMaxValue(
                            MultiplierRange.Steeping.SUBMERGED_TIME,
                            MinRangeValues.Steeping.SUBMERGED_TIME
                        )
                    ),

                    waterVolume = ParametersRange(
                        min = MinRangeValues.Steeping.WATER_VOLUME,
                        max = calculateMaxValue(
                            MultiplierRange.Steeping.WATER_VOLUME,
                            MinRangeValues.Steeping.WATER_VOLUME
                        )
                    ),

                    restTime = ParametersRange(
                        min = MinRangeValues.Steeping.REST_TIME,
                        max = calculateMaxValue(
                            MultiplierRange.Steeping.REST_TIME,
                            MinRangeValues.Steeping.REST_TIME
                        )
                    ),

                    cycles = ParametersRange(
                        min = MinRangeValues.Steeping.CYCLES,
                        max = calculateMaxValue(
                            MultiplierRange.Steeping.CYCLES,
                            MinRangeValues.Steeping.CYCLES
                        )
                    )
                ),
                germination = GerminationParametersRange(

                    rotationLevel = ParametersRange(
                        min = MinRangeValues.Germination.ROTATION_LEVEL,
                        max = calculateMaxValue(
                            MultiplierRange.Germination.ROTATION_LEVEL,
                            MinRangeValues.Germination.ROTATION_LEVEL
                        )
                    ),

                    totalTime = ParametersRange(
                        min = MinRangeValues.Germination.TOTAL_TIME,
                        max = calculateMaxValue(
                            MultiplierRange.Germination.TOTAL_TIME,
                            MinRangeValues.Germination.TOTAL_TIME
                        )
                    ),

                    waterVolume = ParametersRange(
                        min = MinRangeValues.Germination.WATER_VOLUME,
                        max = calculateMaxValue(
                            MultiplierRange.Germination.WATER_VOLUME,
                            MinRangeValues.Germination.WATER_VOLUME
                        )
                    ),

                    waterAddition = ParametersRange(
                        min = MinRangeValues.Germination.WATER_ADDITION,
                        max = calculateMaxValue(
                            MultiplierRange.Germination.WATER_ADDITION,
                            MinRangeValues.Germination.WATER_ADDITION
                        )
                    )
                ),

                kilning = KilningParametersRange(

                    temperature = ParametersRange(
                        min = MinRangeValues.Kilning.TEMPERATURE,
                        max = calculateMaxValue(
                            MultiplierRange.Kilning.TEMPERATURE,
                            MinRangeValues.Kilning.TEMPERATURE
                        )
                    ),

                    time = ParametersRange(
                        min = MinRangeValues.Kilning.TIME,
                        max = calculateMaxValue(
                            MultiplierRange.Kilning.TIME,
                            MinRangeValues.Kilning.TIME
                        )
                    )
                )
            )
        }

        private fun calculateMaxValue(multiplier: Float, minValue: Float): Float {
            return (minValue + 255 * multiplier)
        }
    }
}