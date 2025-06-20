package com.nicolas.maltesp.data.mappers

fun mapToMemoryUsage(convertedData: List<Int>): Float {
    return convertedData[1] + convertedData[2] / 100f
}