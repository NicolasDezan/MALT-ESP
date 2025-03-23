package com.nicolas.maltesp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicolas.maltesp.presentation.ui.navigation.screens.home.MainScaffold
import com.nicolas.maltesp.presentation.ui.navigation.screens.settings.SettingsScreen

@Composable
fun NavigationApp(
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            MainScaffold(
                navController = navController
            )
        }

        composable("settings"){
            SettingsScreen(
                navController = navController
            )
        }

    }
}