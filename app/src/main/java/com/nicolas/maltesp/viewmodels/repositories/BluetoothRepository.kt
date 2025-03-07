package com.nicolas.maltesp.viewmodels.repositories

import com.nicolas.maltesp.others.dataclasses.ParametersState
import kotlinx.coroutines.flow.StateFlow

interface BluetoothRepository {
    val connectedDeviceName: StateFlow<String?>
    val pulseConnection: StateFlow<Boolean>
    val parametersReceived: StateFlow<ParametersState>

    fun connect()
    fun disconnect()
    fun isConnected(): Boolean
    fun sendCommandArray(byteArray: ByteArray)
}