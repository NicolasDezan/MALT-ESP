package com.nicolas.maltesp.core

import android.Manifest
import android.app.Activity
import android.bluetooth.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object BluetoothUtils {

    private var bluetoothGatt: BluetoothGatt? = null
    private var readCharacteristic: BluetoothGattCharacteristic? = null
    private var writeCharacteristic: BluetoothGattCharacteristic? = null

    // Função para solicitar permissões BLE
    @RequiresApi(Build.VERSION_CODES.S)
    fun requestBlePermissions(activity: Activity) {
        val requiredPermissions = arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val missingPermissions = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, missingPermissions.toTypedArray(), 1)
        }
    }

    // Função para conectar ao dispositivo BLE e configurar características
    fun connectToDevice(
        context: Context,
        deviceName: String,
        serviceUuid: String,
        readCharacteristicUuid: String?, // UUID para característica de leitura (opcional)
        writeCharacteristicUuid: String?, // UUID para característica de escrita (opcional)
        onConnected: (String) -> Unit, // Callback chamado quando conectado
        onDisconnected: () -> Unit, // Callback chamado quando desconectado
        onReadUpdate: (String) -> Unit // Callback chamado quando há atualização de leitura
    ) {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permissão BLE negada", Toast.LENGTH_SHORT).show()
            return
        }

        val device = bluetoothAdapter.bondedDevices.find { it.name == deviceName }
        if (device == null) {
            Toast.makeText(context, "Dispositivo não encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        bluetoothGatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                            onConnected(device.name) // Atualiza o estado como conectado
                            gatt.discoverServices()
                        }
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        onDisconnected() // Atualiza o estado como desconectado
                        Toast.makeText(context, "Dispositivo desconectado", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permissão necessária para continuar", Toast.LENGTH_SHORT).show()
                    return
                }

                val service = gatt.getService(java.util.UUID.fromString(serviceUuid))
                readCharacteristic = readCharacteristicUuid?.let {
                    service?.getCharacteristic(java.util.UUID.fromString(it))
                }
                writeCharacteristic = writeCharacteristicUuid?.let {
                    service?.getCharacteristic(java.util.UUID.fromString(it))
                }

                // Configurar notificações para a característica de leitura, se disponível
                readCharacteristic?.let { characteristic ->
                    try {
                        gatt.setCharacteristicNotification(characteristic, true)
                        val descriptor = characteristic.getDescriptor(
                            java.util.UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
                        )
                        descriptor?.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                        gatt.writeDescriptor(descriptor)
                    } catch (e: SecurityException) {
                        Toast.makeText(context, "Erro ao configurar notificações BLE", Toast.LENGTH_SHORT).show()
                    }
                }

                if (readCharacteristic == null && writeCharacteristic == null) {
                    Toast.makeText(context, "Nenhuma característica válida encontrada", Toast.LENGTH_SHORT).show()
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
                if (characteristic.uuid == readCharacteristic?.uuid) {
                    val value = characteristic.value
                    val data = value?.let { String(it) } ?: "N/A"
                    onReadUpdate(data)
                }
            }
        })
    }

    // Função para desconectar o dispositivo BLE
    fun disconnectDevice(context: Context) {
        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                bluetoothGatt?.let { gatt ->
                    // Desativa notificações para a característica de leitura, se configurada
                    readCharacteristic?.let { characteristic ->
                        gatt.setCharacteristicNotification(characteristic, false)
                        val descriptor = characteristic.getDescriptor(
                            java.util.UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
                        )
                        descriptor?.value = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                        gatt.writeDescriptor(descriptor)
                    }

                    gatt.disconnect()
                    gatt.close()
                }
                bluetoothGatt = null
                readCharacteristic = null
                writeCharacteristic = null
            } else {
                Toast.makeText(context, "Permissão BLE negada", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Erro ao desconectar: permissão necessária", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Erro inesperado ao desconectar", Toast.LENGTH_SHORT).show()
        }
    }

    // Função para enviar um comando para a característica de escrita
    // Função para enviar um comando à característica configurada
    fun writeCommand(context: Context, command: Any) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permissão BLE negada", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            writeCharacteristic?.let {
                when (command) {
                    is String -> it.setValue(command) // Passa a String diretamente
                    is ByteArray -> it.setValue(command) // Passa o ByteArray diretamente
                    else -> {
                        Toast.makeText(context, "Tipo de comando inválido", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                bluetoothGatt?.writeCharacteristic(it) // Envia o comando ao dispositivo
            } ?: run {
                Toast.makeText(context, "Característica de escrita não configurada", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Erro ao enviar comando", Toast.LENGTH_SHORT).show()
        }
    }

    fun writeArrayByteCommand(context: Context, array: ByteArray) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permissão BLE negada", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            writeCharacteristic?.let { characteristic ->
                // Definir valor da característica e enviar
                characteristic.value = array
                bluetoothGatt?.writeCharacteristic(characteristic)
            } ?: run {
                Toast.makeText(context, "Característica de escrita não configurada", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Erro ao enviar comando", Toast.LENGTH_SHORT).show()
        }
    }

//    fun writeArrayFloatCommand(context: Context, array: FloatArray) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context, "Permissão BLE negada", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        try {
//            writeCharacteristic?.let { characteristic ->
//                // Converter Array<Float> para ByteArray
//                val byteBuffer = ByteBuffer.allocate(array.size * 4).order(ByteOrder.LITTLE_ENDIAN)
//                array.forEach { byteBuffer.putFloat(it) }
//                val byteArray = byteBuffer.array()
//                // Definir valor da característica e enviar
//                characteristic.value = byteArray
//                bluetoothGatt?.writeCharacteristic(characteristic)
//            } ?: run {
//                Toast.makeText(context, "Característica de escrita não configurada", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: SecurityException) {
//            Toast.makeText(context, "Erro ao enviar comando", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun writeArrayShortCommand(context: Context, array: ShortArray) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context, "Permissão BLE negada", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        try {
//            writeCharacteristic?.let { characteristic ->
//                // Converter Array<Short> para ByteArray
//                val byteBuffer = ByteBuffer.allocate(array.size * 2).order(ByteOrder.LITTLE_ENDIAN)
//                array.forEach { byteBuffer.putShort(it) }
//                val byteArray = byteBuffer.array()
//                // Definir valor da característica e enviar
//                characteristic.value = byteArray
//                bluetoothGatt?.writeCharacteristic(characteristic)
//            } ?: run {
//                Toast.makeText(context, "Característica de escrita não configurada", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: SecurityException) {
//            Toast.makeText(context, "Erro ao enviar comando", Toast.LENGTH_SHORT).show()
//        }
//    }

}
