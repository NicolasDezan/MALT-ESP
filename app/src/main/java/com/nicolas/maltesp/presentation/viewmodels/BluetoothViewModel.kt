package com.nicolas.maltesp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.domain.models.ActuatorUiState
import com.nicolas.maltesp.domain.models.SensorReadUiState
import com.nicolas.maltesp.domain.repositories.BluetoothRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val bluetoothRepository: BluetoothRepository,
) : ViewModel() {

    val connectedDeviceName = bluetoothRepository.connectedDeviceName
    val pulseConnection = bluetoothRepository.pulseConnection
    val parametersReceived = bluetoothRepository.parametersReceived
    val memoryUsage = bluetoothRepository.memoryUsage.map { it?.let { "%.2f%%".format(it) } ?: " ---" }
        .stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = " ---")

    val sensorReadUiState = bluetoothRepository.sensorRead
        .map { sensor ->
            sensor?.let {
                SensorReadUiState(
                    temperature = "%.2f°C".format(it.temperature),
                    humidity = "%.2f%%".format(it.humidity),
                    eco2 = "${it.eco2} ppm"
                )
            } ?: SensorReadUiState("—", "—", "—")
        }
        .stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = SensorReadUiState("—", "—", "—"))

    val actuatorUiState = bluetoothRepository.actuatorState
        .map { state ->
            state?.let {
                ActuatorUiState(
                    entrada = if (it.entrada) "Ligado" else "Desligado",
                    saida = if (it.saida) "Ligado" else "Desligado",
                    rotacao = if (it.rotacao) "Ligado" else "Desligado",
                    resistencia = if (it.resistencia) "Ligado" else "Desligado",
                    bombaAr = if (it.bombaAr) "Ligado" else "Desligado"
                )
            } ?: ActuatorUiState("—", "—", "—", "—", "—")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ActuatorUiState("—", "—", "—", "—", "—")
        )


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
