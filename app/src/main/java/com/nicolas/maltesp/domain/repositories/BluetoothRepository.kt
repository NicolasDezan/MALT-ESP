package com.nicolas.maltesp.domain.repositories

import com.nicolas.maltesp.domain.models.ActuatorState
import com.nicolas.maltesp.domain.models.ParametersState
import com.nicolas.maltesp.domain.models.SensorReadData
import kotlinx.coroutines.flow.StateFlow

interface BluetoothRepository {
    val connectedDeviceName: StateFlow<String?>
    val pulseConnection: StateFlow<Boolean>
    val parametersReceived: StateFlow<ParametersState>
    val memoryUsage: StateFlow<Float?>
    val sensorRead: StateFlow<SensorReadData?>
    val actuatorState: StateFlow<ActuatorState?>
    val processStatus: StateFlow<String?>

    fun isReadToPullParameters(): Boolean

    fun connect(deviceName: String, onPulseReceived: () -> Unit)
    fun disconnect()
    fun isConnected(): Boolean
    fun setPulseConnection(state: Boolean)
    fun sendCommandArray(byteArray: ByteArray)
}