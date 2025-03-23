package com.nicolas.maltesp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.domain.repositories.BluetoothRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val bluetoothRepository: BluetoothRepository,
) : ViewModel() {

    val connectedDeviceName = bluetoothRepository.connectedDeviceName
    val pulseConnection = bluetoothRepository.pulseConnection
    val parametersReceived = bluetoothRepository.parametersReceived

    private var pulseJob: Job? = null

    fun connect() {
        bluetoothRepository.connect(
            deviceName = "ESP32-BLE",
            onPulseReceived = { handlePulseReceived() }
        )
    }

    private fun handlePulseReceived() {
        pulseJob?.cancel() // Cancela qualquer job anterior
        pulseJob = viewModelScope.launch {
            bluetoothRepository.setPulseConnection(true)
            delay(1000) // Mantém o estado true por 1000ms
            bluetoothRepository.setPulseConnection(false) // Volta para false
        }
    }

    fun isReadToPullParameters(): Boolean {
        return bluetoothRepository.isReadToPullParameters()
    }

    fun disconnect() {
        bluetoothRepository.disconnect()
        pulseJob?.cancel()
    }

    fun isConnected() = bluetoothRepository.isConnected()

    fun sendCommandArray(byteArray: ByteArray) = bluetoothRepository.sendCommandArray(byteArray)
}
