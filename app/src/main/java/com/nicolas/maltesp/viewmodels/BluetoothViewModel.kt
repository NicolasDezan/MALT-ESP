package com.nicolas.maltesp.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.nicolas.maltesp.BluetoothUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

const val SERVICE_UUID = "12345678-1234-1234-1234-1234567890ab"
const val WRITE_UUID = "12345678-1234-1234-1234-1234567890ad"
const val READ_UUID = "12345678-1234-1234-1234-1234567890ac"

class BluetoothViewModel(application: Application) : AndroidViewModel(application) {
    private val _temperature = MutableStateFlow<String?>("")
    val temperature = _temperature.asStateFlow()

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    val connectedDeviceName = _connectedDeviceName.asStateFlow()

    fun connect(context: Context) {
        BluetoothUtils.connectToDevice(
            context = context,
            deviceName = "ESP32-BLE",
            onConnected = {
                BluetoothUtils.connectToDevice(
                    context = context,
                    deviceName = "ESP32-BLE",
                    onConnected = { deviceName ->
                        _connectedDeviceName.value = deviceName // Atualiza o nome do dispositivo conectado
                    },
                    onDisconnected = {
                        _connectedDeviceName.value = null
                        _temperature.value = ""
                    },
                    onReadUpdate = { temp ->
                        _temperature.value = temp // Atualiza a temperatura no estado do Compose
                    },
                    serviceUuid = SERVICE_UUID,
                    writeCharacteristicUuid = WRITE_UUID,
                    readCharacteristicUuid = READ_UUID
                )
            },
            onDisconnected = {
                _connectedDeviceName.value = null
                _temperature.value = ""
            },
            onReadUpdate = { temp ->
                _temperature.value = temp
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