package com.nicolas.maltesp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.nicolas.maltesp.data.AppDatabase
import com.nicolas.maltesp.screens.MainScreenWithDrawer
import com.nicolas.maltesp.ui.theme.MaltEspTheme
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.BottomBarViewModel
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
        val bottomBarViewModel by viewModels<BottomBarViewModel>()
        val parametersViewModel: ParametersViewModel by viewModels { parametersDao }
        val bluetoothViewModel by viewModels<BluetoothViewModel>()

        //UI
        setContent {
            MaltEspTheme {
                MainScreenWithDrawer(
                    context = this,
                    bluetoothViewModel = bluetoothViewModel,
                    bottomBarViewModel = bottomBarViewModel,
                    parametersViewModel = parametersViewModel
                )
            }
        }
    }
}
