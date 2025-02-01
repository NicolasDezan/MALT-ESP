package com.nicolas.maltesp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.room.Room
import com.nicolas.maltesp.data.AppDatabase
import com.nicolas.maltesp.screens.MainScreenWithDrawer
import com.nicolas.maltesp.viewmodels.BottomBarViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModelFactory

const val SERVICE_UUID = "12345678-1234-1234-1234-1234567890ab"
const val WRITE_UUID = "12345678-1234-1234-1234-1234567890ad"
const val READ_UUID = "12345678-1234-1234-1234-1234567890ac"


/*################################################################
######################## MAIN ACTIVITY ###########################
##################################################################*/

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // BLUETOOTH
        BluetoothUtils.requestBlePermissions(this)


        // Inicialização do Banco de Dados:
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()


        // DAO
        val parametersDao = ParametersViewModelFactory(db.maltingRecipeDao())


        // VIEW MODELS
        val bottomBarViewModel by viewModels<BottomBarViewModel>()
        val parametersViewModel: ParametersViewModel by viewModels { parametersDao }

        //UI
        setContent {
            var temperature by remember { mutableStateOf("") }
            var connectedDeviceName by remember { mutableStateOf<String?>(null) } // Nome do dispositivo conectado

            MainScreenWithDrawer(
                onConnect = {
                    BluetoothUtils.connectToDevice(
                        context = this, deviceName = "ESP32-BLE",
                        onConnected = { deviceName ->
                            connectedDeviceName = deviceName // Atualiza o nome do dispositivo conectado
                        },
                        onDisconnected = {
                            connectedDeviceName = null
                            temperature = ""
                        },
                        onReadUpdate = { temp ->
                            temperature = temp // Atualiza a temperatura no estado do Compose
                        },
                        serviceUuid = SERVICE_UUID, writeCharacteristicUuid = WRITE_UUID, readCharacteristicUuid = READ_UUID
                    )
                },
                onDisconnect = {
                    BluetoothUtils.disconnectDevice(this)
                    connectedDeviceName = null // Atualiza o estado visual ao desconectar
                    temperature = "" // Limpa a temperatura ao desconectar
                },
                onSendCommand = { command ->
                    BluetoothUtils.writeCommand(this, command)
                },
                onSendCommandArrayFloat = { byteArray ->
                    BluetoothUtils.writeArrayByteCommand(this, byteArray)
                },
                temperature = temperature,
                connectedDeviceName = connectedDeviceName, // Passa o nome para o MyApp

                bottomBarViewModel = bottomBarViewModel,

                parametersViewModel = parametersViewModel
            )
        }
    }
}
