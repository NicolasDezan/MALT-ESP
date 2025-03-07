package com.nicolas.maltesp.viewmodels

import androidx.lifecycle.ViewModel
import com.nicolas.maltesp.viewmodels.repositories.BluetoothRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val repository: BluetoothRepository
) : ViewModel() {

    val connectedDeviceName = repository.connectedDeviceName
    val pulseConnection = repository.pulseConnection
    val parametersReceived = repository.parametersReceived

    fun connect() = repository.connect()
    fun disconnect() = repository.disconnect()
    fun isConnected() = repository.isConnected()
    fun sendCommandArray(byteArray: ByteArray) = repository.sendCommandArray(byteArray)
}