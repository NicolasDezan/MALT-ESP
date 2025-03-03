package com.nicolas.maltesp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.core.BluetoothUtils
import com.nicolas.maltesp.others.classes.Parameters
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BluetoothViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val SERVICE_UUID = "12345678-1234-1234-1234-1234567890ab"
        const val WRITE_UUID = "12345678-1234-1234-1234-1234567890ad"
        const val READ_UUID = "12345678-1234-1234-1234-1234567890ac"
    }

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    val connectedDeviceName = _connectedDeviceName.asStateFlow()

    private var pulseJob: Job? = null
    private val _pulseConection = MutableStateFlow(false)
    val pulseConnection = _pulseConection.asStateFlow()

    private val _parametersReceived = MutableStateFlow(Parameters.initializeParametersState())
    val parametersReceived = _parametersReceived.asStateFlow()


    fun isConnected():Boolean{
        return _connectedDeviceName.value != null
    }

    fun connect(context: Context) {
        BluetoothUtils.connectToDevice(
            context = context,
            deviceName = "ESP32-BLE",
            onConnected = {
                deviceName ->
                _connectedDeviceName.value = deviceName
            },
            onDisconnected = {
                _connectedDeviceName.value = null
                _parametersReceived.value = Parameters.initializeParametersState()
            },
            onReadUpdate = { data ->
                //Triagem de recebimento: PARAMETROS ATUAIS; PING_LIVE; LEITURA DE SENSORES

                println("Received data: $data")

                _parametersReceived.value = Parameters.testParametersState() // -> Vai virar um recebimento ja ja

                // Cancela o pulso anterior, se existir
                pulseJob?.cancel()

                // Ativa o pulso
                _pulseConection.value = true

                // Agenda a desativação do pulso após 500 ms
                pulseJob = viewModelScope.launch {
                    delay(1000) // Aguarda 500 ms aiiii que gastura ta muito rapido
                    _pulseConection.value = false
                }
            },
            serviceUuid = SERVICE_UUID,
            writeCharacteristicUuid = WRITE_UUID,
            readCharacteristicUuid = READ_UUID
        )
    }

    fun disconnect(context: Context) {
        BluetoothUtils.disconnectDevice(context)
        _connectedDeviceName.value = null
    }

//    fun sendCommandString(context: Context, command: String) {
//        BluetoothUtils.writeCommand(context, command)
//    }

    fun sendCommandArray(context: Context, byteArray: ByteArray) {
        BluetoothUtils.writeArrayByteCommand(context, byteArray)
    }

        private fun convertBytesESPToIntKotlin(espBytes: ByteArray): List<Int> {
            val byteArray = espBytes.map { (it.toInt() - 128).toByte() }.toByteArray()
            val intArray = byteArray.map { it.toInt() + 128 }
            return intArray
        }

}