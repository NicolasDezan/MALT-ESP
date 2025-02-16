package com.nicolas.maltesp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.nicolas.maltesp.core.BluetoothUtils
import com.nicolas.maltesp.data.dataclasses.initializeParametersState
import com.nicolas.maltesp.data.dataclasses.testParametersState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BluetoothViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val SERVICE_UUID = "12345678-1234-1234-1234-1234567890ab"
        const val WRITE_UUID = "12345678-1234-1234-1234-1234567890ad"
        const val READ_UUID = "12345678-1234-1234-1234-1234567890ac"
    }

    private val _temperature = MutableStateFlow<String?>("")
    val temperature = _temperature.asStateFlow()

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    val connectedDeviceName = _connectedDeviceName.asStateFlow()

    private val _parametersReceived = MutableStateFlow(initializeParametersState())
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
                _temperature.value = ""
                _parametersReceived.value = initializeParametersState()
            },
            onReadUpdate = { temp ->
                _temperature.value = temp
                _parametersReceived.value = testParametersState() // TODO: Implementar o receive real no ESP32
            },
            serviceUuid = SERVICE_UUID,
            writeCharacteristicUuid = WRITE_UUID,
            readCharacteristicUuid = READ_UUID
        )
    }

    fun disconnect(context: Context) {
        BluetoothUtils.disconnectDevice(context)
        _connectedDeviceName.value = null
        _temperature.value = ""
    }

    fun sendCommand(context: Context, command: String) {
        BluetoothUtils.writeCommand(context, command)
    }

    fun sendCommandArray(context: Context, byteArray: ByteArray) {
        BluetoothUtils.writeArrayByteCommand(context, byteArray)
    }
}