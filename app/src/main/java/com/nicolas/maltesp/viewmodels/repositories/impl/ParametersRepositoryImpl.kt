package com.nicolas.maltesp.viewmodels.repositories.impl

import com.nicolas.maltesp.others.classes.Parameters
import com.nicolas.maltesp.others.dataclasses.ParametersRangeGroup
import com.nicolas.maltesp.others.dataclasses.ParametersState
import com.nicolas.maltesp.viewmodels.repositories.ParametersRepository
import com.nicolas.maltesp.viewmodels.repositories.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class ParametersRepositoryImpl @Inject constructor(private val sharedRepository: SharedRepository) :
    ParametersRepository {

    private val _parametersRange = MutableStateFlow(Parameters.initializeRangeGroup())

    override fun getParametersRange(): StateFlow<ParametersRangeGroup> {
        return _parametersRange.asStateFlow()
    }

    override fun compareSteepingSubmergedTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.steeping.submergedTime.value.toFloat() == parametersReceived.steeping.submergedTime.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareSteepingWaterVolume(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.steeping.waterVolume.value.toFloat() == parametersReceived.steeping.waterVolume.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareSteepingRestTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.steeping.restTime.value.toFloat() == parametersReceived.steeping.restTime.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareSteepingCycles(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.steeping.cycles.value.toFloat() == parametersReceived.steeping.cycles.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareGerminationTotalTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.germination.totalTime.value.toFloat() == parametersReceived.germination.totalTime.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareGerminationWaterVolume(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.germination.waterVolume.value.toFloat() == parametersReceived.germination.waterVolume.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareGerminationWaterAddition(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.germination.waterAddition.value.toFloat() == parametersReceived.germination.waterAddition.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareGerminationRotationLevel(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.germination.rotationLevel.value.toFloat() == parametersReceived.germination.rotationLevel.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareKilningTemperature(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try{
            if(isBluetoothConnected) {
                sharedRepository.getParameters().value.kilning.temperature.value.toFloat() == parametersReceived.kilning.temperature.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun compareKilningTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean): Boolean? {
        return try {
            if (isBluetoothConnected) {
                sharedRepository.getParameters().value.kilning.time.value.toFloat() == parametersReceived.kilning.time.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }


    override fun isAllParametersValid(): Boolean {
        return  isSteepingSubmergedTimeValid() &&
                isSteepingWaterVolumeValid() &&
                isSteepingRestTimeValid() &&
                isSteepingCyclesValid() &&
                isGerminationTotalTimeValid() &&
                isGerminationWaterVolumeValid() &&
                isGerminationWaterAdditionValid() &&
                isGerminationRotationLevelValid() &&
                isKilningTemperatureValid() &&
                isKilningTimeValid()
    }

    override fun isSteepingSubmergedTimeValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.steeping.submergedTime.min,
            rangeMax = _parametersRange.value.steeping.submergedTime.max,
            valueString = sharedRepository.getParameters().value.steeping.submergedTime.value
        )
    }

    override fun isSteepingWaterVolumeValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.steeping.waterVolume.min,
            rangeMax = _parametersRange.value.steeping.waterVolume.max,
            valueString = sharedRepository.getParameters().value.steeping.waterVolume.value
        )
    }

    override fun isSteepingRestTimeValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.steeping.restTime.min,
            rangeMax = _parametersRange.value.steeping.restTime.max,
            valueString = sharedRepository.getParameters().value.steeping.restTime.value
        )
    }

    override fun isSteepingCyclesValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.steeping.cycles.min,
            rangeMax = _parametersRange.value.steeping.cycles.max,
            valueString = sharedRepository.getParameters().value.steeping.cycles.value
        )
    }

    override fun isGerminationTotalTimeValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.germination.totalTime.min,
            rangeMax = _parametersRange.value.germination.totalTime.max,
            valueString = sharedRepository.getParameters().value.germination.totalTime.value
        )
    }

    override fun isGerminationWaterVolumeValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.germination.waterVolume.min,
            rangeMax = _parametersRange.value.germination.waterVolume.max,
            valueString = sharedRepository.getParameters().value.germination.waterVolume.value
        )
    }

    override fun isGerminationWaterAdditionValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.germination.waterAddition.min,
            rangeMax = _parametersRange.value.germination.waterAddition.max,
            valueString = sharedRepository.getParameters().value.germination.waterAddition.value
        )
    }

    override fun isGerminationRotationLevelValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.germination.rotationLevel.min,
            rangeMax = _parametersRange.value.germination.rotationLevel.max,
            valueString = sharedRepository.getParameters().value.germination.rotationLevel.value
        )
    }

    override fun isKilningTemperatureValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.kilning.temperature.min,
            rangeMax = _parametersRange.value.kilning.temperature.max,
            valueString = sharedRepository.getParameters().value.kilning.temperature.value
        )
    }

    override fun isKilningTimeValid(): Boolean {
        return isSomethingValid(
            rangeMin = _parametersRange.value.kilning.time.min,
            rangeMax = _parametersRange.value.kilning.time.max,
            valueString = sharedRepository.getParameters().value.kilning.time.value
        )
    }


    // TODO: Tem que sair daqui essa função
    private fun isSomethingValid(rangeMin: Float, rangeMax: Float, valueString: String): Boolean {
        return try{
            val value = valueString.toFloat()
            value in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }
}
