package com.nicolas.maltesp.data.repositories

import android.content.Context
import com.nicolas.maltesp.data.core.BluetoothUtils
import com.nicolas.maltesp.data.mappers.ParametersMapper
import com.nicolas.maltesp.domain.models.Parameters
import com.nicolas.maltesp.domain.repositories.BluetoothRepository
import com.nicolas.maltesp.domain.repositories.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class BluetoothRepositoryImpl @Inject constructor(
    private val context: Context,
    private val sharedRepository: SharedRepository,
) : BluetoothRepository {

    private val serviceUUID = "12345678-1234-1234-1234-1234567890ab"
    private val writeUUID = "12345678-1234-1234-1234-1234567890ad"
    private val readUUID = "12345678-1234-1234-1234-1234567890ac"

    private val _connectedDeviceName = MutableStateFlow<String?>(null)
    override val connectedDeviceName = _connectedDeviceName.asStateFlow()

    private val _pulseConnection = MutableStateFlow(false)
    override val pulseConnection = _pulseConnection.asStateFlow()

    private val _parametersReceived = MutableStateFlow(Parameters.initializeParametersState())
    override val parametersReceived = _parametersReceived.asStateFlow()

    private val _isBluetoothConnected = MutableStateFlow(false)

    override fun isReadToPullParameters(): Boolean {
        return _parametersReceived.value != Parameters.initializeParametersState() && _isBluetoothConnected.value
    }

    private val _memoryUsage = MutableStateFlow<Float?>(null)
    override val memoryUsage = _memoryUsage.asStateFlow()

    private fun updateMemoryUsage(newMemoryUsage: Float) {
        _memoryUsage.value = newMemoryUsage
    }

    override fun connect(deviceName: String, onPulseReceived: () -> Unit) {
        BluetoothUtils.connectToDevice(
            context = context,
            deviceName = deviceName,
            onConnected = { name -> _connectedDeviceName.value = name },
            onDisconnected = {
                _connectedDeviceName.value = null
                _parametersReceived.value = Parameters.initializeParametersState()
                _pulseConnection.value = false
                _memoryUsage.value = null
            },
            onReadUpdate = { data ->
                val convertedData = convertBytesESPToIntKotlin(data)

                println("Data received: $convertedData")

                if (convertedData[0] == 255) {
                    onPulseReceived()
                    val memUseData = convertedData[1].toFloat() + convertedData[2].toFloat() / 100
                    updateMemoryUsage(memUseData)
                }

                if (convertedData[0] == 1) {
                    println("Change Parameters: $convertedData")
                    _parametersReceived.value = ParametersMapper.receiveParameters(convertedData)
                    sharedRepository.updateParameters(_parametersReceived.value)
                }


            },
            serviceUuid = serviceUUID,
            writeCharacteristicUuid = writeUUID,
            readCharacteristicUuid = readUUID
        )
    }

    override fun disconnect() {
        BluetoothUtils.disconnectDevice(context)
        _pulseConnection.value = false
        _connectedDeviceName.value = null
        _memoryUsage.value = null
    }

    override fun isConnected(): Boolean {
        _isBluetoothConnected.value = _connectedDeviceName.value != null
        return _connectedDeviceName.value != null
    }

    override fun sendCommandArray(byteArray: ByteArray) {
        BluetoothUtils.writeArrayByteCommand(context, byteArray)
    }

    override fun setPulseConnection(state: Boolean) {
        _pulseConnection.value = state
    }

    private fun convertBytesESPToIntKotlin(espBytes: ByteArray): List<Int> {
        return espBytes.map { (it.toInt() - 128).toByte() }.map { it.toInt() + 128 }
    }
}
