package com.nicolas.maltesp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.domain.models.ParametersState
import com.nicolas.maltesp.domain.constants.MinRangeValues
import com.nicolas.maltesp.domain.constants.MultiplierRange
import com.nicolas.maltesp.domain.repositories.ParametersRepository
import com.nicolas.maltesp.domain.repositories.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParametersViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val parametersRepository: ParametersRepository
) : ViewModel() {

    val parametersState = sharedRepository.getParameters()

    val parametersRange = parametersRepository.getParametersRange()


    fun compareSteepingSubmergedTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareSteepingSubmergedTime(parametersReceived, isBluetoothConnected)
    }

    fun compareSteepingWaterVolume(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareSteepingWaterVolume(parametersReceived, isBluetoothConnected)
    }

    fun compareSteepingRestTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareSteepingRestTime(parametersReceived, isBluetoothConnected)
    }

    fun compareSteepingCycles(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareSteepingCycles(parametersReceived, isBluetoothConnected)
    }

    fun compareGerminationTotalTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareGerminationTotalTime(parametersReceived, isBluetoothConnected)
    }

    fun compareGerminationWaterVolume(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareGerminationWaterVolume(parametersReceived, isBluetoothConnected)
    }

    fun compareGerminationWaterAddition(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareGerminationWaterAddition(parametersReceived, isBluetoothConnected)
    }

    fun compareGerminationRotationLevel(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareGerminationRotationLevel(parametersReceived, isBluetoothConnected)
    }

    fun compareKilningTemperature(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareKilningTemperature(parametersReceived, isBluetoothConnected)
    }

    fun compareKilningTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return parametersRepository.compareKilningTime(parametersReceived, isBluetoothConnected)
    }

    fun isAllParametersValid(): Boolean {
        return parametersRepository.isAllParametersValid()
    }

    fun isSteepingSubmergedTimeValid(): Boolean {
        return parametersRepository.isSteepingSubmergedTimeValid()
    }

    fun isSteepingWaterVolumeValid(): Boolean {
        return parametersRepository.isSteepingWaterVolumeValid()
    }

    fun isSteepingRestTimeValid(): Boolean {
        return parametersRepository.isSteepingRestTimeValid()
    }

    fun isSteepingCyclesValid(): Boolean {
        return parametersRepository.isSteepingCyclesValid()
    }

    fun isGerminationTotalTimeValid(): Boolean {
        return parametersRepository.isGerminationTotalTimeValid()
    }

    fun isGerminationWaterVolumeValid(): Boolean {
        return parametersRepository.isGerminationWaterVolumeValid()
    }

    fun isGerminationWaterAdditionValid(): Boolean {
        return parametersRepository.isGerminationWaterAdditionValid()
    }

    fun isGerminationRotationLevelValid(): Boolean {
        return parametersRepository.isGerminationRotationLevelValid()
    }

    fun isKilningTemperatureValid(): Boolean {
        return parametersRepository.isKilningTemperatureValid()
    }

    fun isKilningTimeValid(): Boolean {
        return parametersRepository.isKilningTimeValid()
    }

    // FUNÇÃO AUXILIAR: RELACIONADA COM PREPARAR UM ARRAY PARA SER ENVIADO VIA COMUNICAÇÃO BLUETOOTH
    fun parametersToByteArray(): ByteArray? {
        return try {
            val bytes = byteArrayOf(
                /*...0...*/(-128), // == 0: IDENTIFICADOR DE PARAMETROS -- WRITE

                /*...1...*/parameterToByte(sharedRepository.getParameters().value.steeping.submergedTime.value, MinRangeValues.Steeping.SUBMERGED_TIME, MultiplierRange.Steeping.SUBMERGED_TIME),
                /*...2...*/parameterToByte(sharedRepository.getParameters().value.steeping.waterVolume.value, MinRangeValues.Steeping.WATER_VOLUME, MultiplierRange.Steeping.WATER_VOLUME),
                /*...3...*/parameterToByte(sharedRepository.getParameters().value.steeping.restTime.value, MinRangeValues.Steeping.REST_TIME, MultiplierRange.Steeping.REST_TIME),
                /*...4...*/parameterToByte(sharedRepository.getParameters().value.steeping.cycles.value, MinRangeValues.Steeping.CYCLES, MultiplierRange.Steeping.CYCLES),

                /*...5...*/parameterToByte(sharedRepository.getParameters().value.germination.rotationLevel.value, MinRangeValues.Germination.ROTATION_LEVEL, MultiplierRange.Germination.ROTATION_LEVEL),
                /*...6...*/parameterToByte(sharedRepository.getParameters().value.germination.totalTime.value, MinRangeValues.Germination.TOTAL_TIME, MultiplierRange.Germination.TOTAL_TIME),
                /*...7...*/parameterToByte(sharedRepository.getParameters().value.germination.waterVolume.value, MinRangeValues.Germination.WATER_VOLUME, MultiplierRange.Germination.WATER_VOLUME),
                /*...8...*/parameterToByte(sharedRepository.getParameters().value.germination.waterAddition.value, MinRangeValues.Germination.WATER_ADDITION, MultiplierRange.Germination.WATER_ADDITION),

                /*...9...*/parameterToByte(sharedRepository.getParameters().value.kilning.temperature.value, MinRangeValues.Kilning.TEMPERATURE, MultiplierRange.Kilning.TEMPERATURE),
                /*...10..*/parameterToByte(sharedRepository.getParameters().value.kilning.time.value, MinRangeValues.Kilning.TIME, MultiplierRange.Kilning.TIME)
            )
            bytes // Retorno do array de bytes
        } catch (e: NumberFormatException) {
            null // Retorno nulo em caso de exceção
        }
    }

    // FUNÇÃO AUXILIAR RELACIONADA COM A FUNÇÃO ACIMA: parametersToByteArray
        // Transformo os valores em um int de 0 a 255 e depois subtraio por 128 para que os valores fiquem entre -128 e 127
    private fun parameterToByte(parameter: String, minParameterValue: Float, multiplierRange: Float): Byte {
        val byteComum = ((parameter.toFloat() - minParameterValue) / multiplierRange).toInt()
        val byteKotlin = ((byteComum - 128).toByte())
        return byteKotlin
    }
}