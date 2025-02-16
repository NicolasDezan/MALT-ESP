package com.nicolas.maltesp.data.objects

import com.nicolas.maltesp.data.dataclasses.ParametersRange

object SteepingParametersRange {
    val SubmergedTime = ParametersRange(min = 0f,max = 100f)
    val WaterVolume = ParametersRange(min = 0f,max = 100f)
    val RestTime = ParametersRange(min = 0f,max = 100f)
    val Cycles = ParametersRange(min = 0f,max = 100f)
}

object GerminationParametersRange {
    val TotalTime = ParametersRange(min = 0f,max = 100f)
    val WaterVolume = ParametersRange(min = 0f, 100f)
    val WaterAddition = ParametersRange(0f, 100f)
    val RotationLevel = ParametersRange(0f, 100f)
}

object KilningParametersRange {
    val Temperature = ParametersRange(0f, 100f)
    val Time = ParametersRange(0f, 100f)
}