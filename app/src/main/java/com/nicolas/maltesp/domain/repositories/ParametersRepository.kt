package com.nicolas.maltesp.domain.repositories

import com.nicolas.maltesp.domain.models.ParametersRangeGroup
import com.nicolas.maltesp.domain.models.ParametersState
import kotlinx.coroutines.flow.StateFlow

interface ParametersRepository {
    fun getParametersRange(): StateFlow<ParametersRangeGroup>

    fun compareSteepingSubmergedTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareSteepingWaterVolume(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareSteepingRestTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareSteepingCycles(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareGerminationTotalTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareGerminationWaterVolume(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareGerminationWaterAddition(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareGerminationRotationLevel(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareKilningTemperature(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?
    fun compareKilningTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean?

    fun isAllParametersValid(): Boolean
    fun isSteepingSubmergedTimeValid(): Boolean
    fun isSteepingWaterVolumeValid(): Boolean
    fun isSteepingRestTimeValid(): Boolean
    fun isSteepingCyclesValid(): Boolean
    fun isGerminationTotalTimeValid(): Boolean
    fun isGerminationWaterVolumeValid(): Boolean
    fun isGerminationWaterAdditionValid(): Boolean
    fun isGerminationRotationLevelValid(): Boolean
    fun isKilningTemperatureValid(): Boolean
    fun isKilningTimeValid(): Boolean
}

