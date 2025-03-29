package com.nicolas.maltesp.domain.repositories

import com.nicolas.maltesp.domain.models.ParametersState
import kotlinx.coroutines.flow.StateFlow

interface BluetoothRepository {
    val connectedDeviceName: StateFlow<String?>
    val pulseConnection: StateFlow<Boolean>
    val parametersReceived: StateFlow<ParametersState>
    val memoryUsage: StateFlow<Float?>

    fun isReadToPullParameters(): Boolean

    fun connect(deviceName: String, onPulseReceived: () -> Unit)
    fun disconnect()
    fun isConnected(): Boolean
    fun setPulseConnection(state: Boolean)
    fun sendCommandArray(byteArray: ByteArray)
}