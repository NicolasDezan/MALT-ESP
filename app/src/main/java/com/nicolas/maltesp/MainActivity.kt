package com.nicolas.maltesp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nicolas.maltesp.core.BluetoothUtils
import com.nicolas.maltesp.ui.navigation.NavigationApp
import com.nicolas.maltesp.ui.theme.MaltEspTheme
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.SettingsViewModel
import com.nicolas.maltesp.viewmodels.factory.SettingsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

/*################################################################
######################## MAIN ACTIVITY ###########################
##################################################################*/

// Extensão para acessar o DataStore no contexto da Activity
private val ComponentActivity.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // BLUETOOTH
        BluetoothUtils.requestPermissions(this)

        // DATASTORE
        val settingsViewModelFactory = SettingsViewModelFactory(dataStore)

        // VIEW MODELS
        val bluetoothViewModel by viewModels<BluetoothViewModel>()
        val settingsViewModel: SettingsViewModel by viewModels { settingsViewModelFactory }

        //UI
        setContent {
            MaltEspTheme(
                darkTheme = settingsViewModel.themeFlow.collectAsState(initial = settingsViewModel.getTheme()).value
            ) {
                NavigationApp(
                    context = this,
                    bluetoothViewModel = bluetoothViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}
