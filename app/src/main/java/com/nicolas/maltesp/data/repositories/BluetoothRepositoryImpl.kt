package com.nicolas.maltesp.data.repositories

import android.content.Context
import com.nicolas.maltesp.data.core.BluetoothUtils
import com.nicolas.maltesp.data.mappers.*
import com.nicolas.maltesp.domain.models.ActuatorState
import com.nicolas.maltesp.domain.models.Parameters
import com.nicolas.maltesp.domain.models.SensorReadData
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

    override fun isReadToPullParameters(): Boolean =
        _parametersReceived.value != Parameters.initializeParametersState() && _isBluetoothConnected.value

    private val _memoryUsage = MutableStateFlow<Float?>(null)
    override val memoryUsage = _memoryUsage.asStateFlow()

    private val _sensorRead = MutableStateFlow<SensorReadData?>(null)
    override val sensorRead = _sensorRead.asStateFlow()

    private val _actuatorState = MutableStateFlow<ActuatorState?>(null)
    override val actuatorState = _actuatorState.asStateFlow()

    private val _processStatus = MutableStateFlow("???")
    override val processStatus = _processStatus.asStateFlow()

    private fun updateMemoryUsage(newMemoryUsage: Float) {
        _memoryUsage.value = newMemoryUsage
    }

    private fun updateSensorRead(newSensorRead: SensorReadData) {
        _sensorRead.value = newSensorRead
    }

    private fun updateActuatorState(newActuatorState: ActuatorState) {
        _actuatorState.value = newActuatorState
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

                when (convertedData[0]) {
                    1 -> handleParameters(convertedData)
                    2 -> handleSensorData(convertedData)
                    3 -> handleActuatorData(convertedData)
                    255 -> handleSystemData(convertedData, onPulseReceived)
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

    private fun handleParameters(data: List<Int>) {
        println("[DEBUG] Change Parameters received: $data")
        _parametersReceived.value = receiveParameters(data)
        sharedRepository.updateParameters(_parametersReceived.value)
    }

    private fun handleSystemData(data: List<Int>, onPulseReceived: () -> Unit) {
        println("[DEBUG] Heartbeat received: $data")
        onPulseReceived()
        updateMemoryUsage(mapToMemoryUsage(data))
        try{_processStatus.value = readProcessStatus(data)}
        catch (e: Exception){
            println("Tem erro rolando $e")
        }

    }

    private fun handleSensorData(data: List<Int>) {
        println("[DEBUG] Sensors received: $data")
        updateSensorRead(mapToSensorReadData(data))
    }

    private fun handleActuatorData(data: List<Int>) {
        println("[DEBUG] Actuators received: $data")
        updateActuatorState(mapToActuatorState(data))
    }
}
