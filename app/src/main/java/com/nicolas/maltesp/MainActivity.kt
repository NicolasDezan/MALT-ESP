package com.nicolas.maltesp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.core.BluetoothUtils
import com.nicolas.maltesp.ui.navigation.NavigationApp
import com.nicolas.maltesp.ui.theme.MaltEspTheme
import com.nicolas.maltesp.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

/*################################################################
######################## MAIN ACTIVITY ###########################
##################################################################*/

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // BLUETOOTH
        BluetoothUtils.requestPermissions(this)

        //UI
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()

            MaltEspTheme(
                darkTheme = settingsViewModel.themeFlow.collectAsState().value
            ) {
                NavigationApp()
            }
        }
    }
}
