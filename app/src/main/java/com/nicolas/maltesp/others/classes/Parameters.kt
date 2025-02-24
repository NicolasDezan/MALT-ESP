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

        fun testParametersState(): ParametersState {
            return ParametersState(
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

        private fun calculateMaxValue(multiplier: Int, minValue: Float): Float {
            return (minValue + 255 * multiplier)
        }
    }
}