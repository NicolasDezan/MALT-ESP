package com.nicolas.maltesp.viewmodels.repositories.impl

import android.content.Context
import com.nicolas.maltesp.core.BluetoothUtils
import com.nicolas.maltesp.others.classes.Parameters
import com.nicolas.maltesp.viewmodels.repositories.BluetoothRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BluetoothRepositoryImpl @Inject constructor(
    private val context: Context
) : BluetoothRepository {

    private val serviceUUID = "12345678-1234-1234-1234-1234567890ab"
    private val writeUUID = "12345678-1234-1234-1234-1234567890ad"
    private val readUUID = "12345678-1234-1234-1234-1234567890ac"

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    override val connectedDeviceName = _connectedDeviceName.asStateFlow()

    private var pulseJob: Job? = null
    private val _pulseConnection = MutableStateFlow(false)
    override val pulseConnection = _pulseConnection.asStateFlow()

    private val _parametersReceived = MutableStateFlow(Parameters.initializeParametersState())
    override val parametersReceived = _parametersReceived.asStateFlow()

    override fun connect() {
        BluetoothUtils.connectToDevice(
            context = context,
            deviceName = "ESP32-BLE",
            onConnected = { deviceName ->
                _connectedDeviceName.value = deviceName
            },
            onDisconnected = {
                _connectedDeviceName.value = null
                _parametersReceived.value = Parameters.initializeParametersState()
            },
            onReadUpdate = { data ->
                val convertedData = convertBytesESPToIntKotlin(data)
                if (convertedData[0] == 1) {
                    _parametersReceived.value = Parameters.receiveParameters(convertedData)
                }

                pulseJob?.cancel()
                _pulseConnection.value = true

                pulseJob = CoroutineScope(Dispatchers.IO).launch {
                    delay(1000)
                    _pulseConnection.value = false
                }
            },
            serviceUuid = serviceUUID,
            writeCharacteristicUuid = writeUUID,
            readCharacteristicUuid = readUUID
        )
    }

    override fun disconnect() {
        BluetoothUtils.disconnectDevice(context)
        _connectedDeviceName.value = null
    }

    override fun isConnected(): Boolean {
        return _connectedDeviceName.value != null
    }

    override fun sendCommandArray(byteArray: ByteArray) {
        BluetoothUtils.writeArrayByteCommand(context, byteArray)
    }

    private fun convertBytesESPToIntKotlin(espBytes: ByteArray): List<Int> {
        return espBytes.map { (it.toInt() - 128).toByte() }.map { it.toInt() + 128 }
    }
}
