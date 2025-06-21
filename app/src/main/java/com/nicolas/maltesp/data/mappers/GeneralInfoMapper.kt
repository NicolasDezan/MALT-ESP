package com.nicolas.maltesp.data.mappers

fun mapToMemoryUsage(convertedData: List<Int>): Float {
    return convertedData[1] + convertedData[2] / 100f
}

fun readProcessStatus(data: List<Int>):String{
    return when (data[3]){
        1 -> "Conectado"
        2 -> "Maceração"
        3 -> "Germinação"
        4 -> "Secagem"
        else -> "???"
    }
}