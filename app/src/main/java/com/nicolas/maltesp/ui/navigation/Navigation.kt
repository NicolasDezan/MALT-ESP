package com.nicolas.maltesp.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicolas.maltesp.ui.navigation.screens.home.MainScaffold
import com.nicolas.maltesp.ui.navigation.screens.settings.SettingsScreen
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.SettingsViewModel

@Composable
fun NavigationApp(
    context: Context,
    bluetoothViewModel : BluetoothViewModel,
    settingsViewModel: SettingsViewModel
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            MainScaffold(
                context = context,
                bluetoothViewModel = bluetoothViewModel,
                navController = navController
            )
        }

        composable("settings"){
            SettingsScreen(
                settingsViewModel = settingsViewModel,
                navController = navController
            )
        }

    }
}