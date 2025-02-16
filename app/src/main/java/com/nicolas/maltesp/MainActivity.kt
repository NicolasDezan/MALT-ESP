package com.nicolas.maltesp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.nicolas.maltesp.core.BluetoothUtils
import com.nicolas.maltesp.data.AppDatabase
import com.nicolas.maltesp.ui.scaffold.ScaffoldWithDrawer
import com.nicolas.maltesp.ui.theme.MaltEspTheme
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ScaffoldViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModelFactory

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
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name").build()

        // DAO
        val parametersDao = ParametersViewModelFactory(db.maltingRecipeDao())


        // VIEW MODELS
        val scaffoldViewModel by viewModels<ScaffoldViewModel>()
        val parametersViewModel: ParametersViewModel by viewModels { parametersDao }
        val bluetoothViewModel by viewModels<BluetoothViewModel>()

        //UI
        setContent {
            MaltEspTheme {
                ScaffoldWithDrawer(
                    context = this,
                    bluetoothViewModel = bluetoothViewModel,
                    scaffoldViewModel = scaffoldViewModel,
                    parametersViewModel = parametersViewModel
                )
            }
        }
    }
}
