package com.nicolas.maltesp.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicolas.maltesp.ui.navigation.scaffold.ScaffoldWithDrawer
import com.nicolas.maltesp.ui.navigation.screens.SettingsScreen
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import com.nicolas.maltesp.viewmodels.ScaffoldViewModel
import com.nicolas.maltesp.viewmodels.SettingsViewModel

@Composable
fun NavigationApp(
    context: Context,
    bluetoothViewModel : BluetoothViewModel,
    scaffoldViewModel: ScaffoldViewModel,
    parametersViewModel: ParametersViewModel,
    settingsViewModel: SettingsViewModel
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home-scaffold"
    ){
        composable("home-scaffold"){
            ScaffoldWithDrawer(
                context = context,
                bluetoothViewModel = bluetoothViewModel,
                scaffoldViewModel = scaffoldViewModel,
                parametersViewModel = parametersViewModel,
                settingsViewModel = settingsViewModel,
                navController = navController
            )
        }

        composable("settings"){
            SettingsScreen()
        }

    }
}