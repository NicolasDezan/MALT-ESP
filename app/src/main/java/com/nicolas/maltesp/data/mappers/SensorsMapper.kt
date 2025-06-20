package com.nicolas.maltesp.data.mappers

import com.nicolas.maltesp.domain.models.SensorReadData


fun mapToSensorReadData(convertedData: List<Int>): SensorReadData {
    return SensorReadData(
        humidity = convertedData[1] + convertedData[2] / 100f,
        temperature = convertedData[3] + convertedData[4] / 100f,
        eco2 = (convertedData[5] shl 16) or (convertedData[6] shl 8) or convertedData[7]
    )
}