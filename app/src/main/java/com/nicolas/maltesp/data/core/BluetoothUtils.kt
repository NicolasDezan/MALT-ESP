package com.nicolas.maltesp.data.core

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

    @RequiresApi(Build.VERSION_CODES.S)
    fun requestPermissions(activity: Activity) {
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

    // Função para conectar ao dispositivo e configurar características
    @Suppress("DEPRECATION")
    fun connectToDevice(
        context: Context,
        deviceName: String,
        serviceUuid: String,
        readCharacteristicUuid: String?,
        writeCharacteristicUuid: String?,
        onConnected: (String) -> Unit,
        onDisconnected: () -> Unit,
        onReadUpdate: (ByteArray) -> Unit
    ) {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Dispositivo sem Bluetooth", Toast.LENGTH_SHORT).show()
            return
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permissão Bluetooth negada\nPermita a permissão Bluetooth no aplicativo", Toast.LENGTH_SHORT).show()
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
                        descriptor?.let {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                // API 33 (Android 13)
                                gatt.writeDescriptor(it, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
                            } else {
                                // Este bloco else utiliza um método depreciado para rodar em dispositivos mais antigos
                                it.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                                gatt.writeDescriptor(it)
                            }
                        }
                    } catch (e: SecurityException) {
                        Toast.makeText(context, "Erro em readCharacteristic", Toast.LENGTH_SHORT)
                            .show()
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
                    //val data = value?.let { String(it) } ?: "N/A" //Se quiser receber String esse é o caminho...
                    onReadUpdate(value)
                }
            }
        })
    }


    @Suppress("DEPRECATION")
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
                        descriptor?.let {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                // API 33 (Android 13)
                                gatt.writeDescriptor(it, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
                            } else {
                                // Este bloco else utiliza um método depreciado para rodar em dispositivos mais antigos
                                it.value = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                                gatt.writeDescriptor(it)
                            }
                        }
                    }

                    gatt.disconnect()
                    gatt.close()
                }
                bluetoothGatt = null
                readCharacteristic = null
                writeCharacteristic = null
            } else {
                Toast.makeText(context, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Erro ao desconectar: permissão necessária", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Erro inesperado ao desconectar", Toast.LENGTH_SHORT).show()
        }
    }

    @Suppress("DEPRECATION")
    fun writeArrayByteCommand(context: Context, array: ByteArray) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permissão negada", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            writeCharacteristic?.let { characteristic ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // API 33 (Android 13) ou superior: usar o novo método
                    bluetoothGatt?.writeCharacteristic(characteristic, array, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                } else {
                    // Este bloco else utiliza um método depreciado para rodar em dispositivos mais antigos
                    characteristic.value = array
                    bluetoothGatt?.writeCharacteristic(characteristic)
                }

            } ?: run {
                Toast.makeText(context, "Erro em writeCharacteristic", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Erro ao enviar comando", Toast.LENGTH_SHORT).show()
        }
    }
}