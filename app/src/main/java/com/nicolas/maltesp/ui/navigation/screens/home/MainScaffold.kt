package com.nicolas.maltesp.ui.navigation.screens.home

import android.content.Context
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.nicolas.maltesp.ui.navigation.drawer.AppDrawer
import com.nicolas.maltesp.ui.navigation.screens.home.actionbutton.SettingFloatingActionButton
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.ContentBottomBar
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.IconButtonsBottomBar
import com.nicolas.maltesp.ui.navigation.screens.home.topbar.SettingTopAppBar
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import com.nicolas.maltesp.viewmodels.ScaffoldViewModel

@Composable
fun MainScaffold(
    context: Context,
    bluetoothViewModel : BluetoothViewModel,
    scaffoldViewModel: ScaffoldViewModel,
    parametersViewModel: ParametersViewModel,
    navController: NavController,
    ){
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    AppDrawer(
        navController = navController,
        drawerState = drawerState,
        scope = scope
    ) {
        Scaffold(
            topBar = { SettingTopAppBar(
                scope = scope,
                drawerState = drawerState,
                scaffoldViewModel = scaffoldViewModel,
                bluetoothViewModel = bluetoothViewModel
            ) },

            floatingActionButton = {
                SettingFloatingActionButton(
                    context = context,
                    parametersViewModel = parametersViewModel,
                    scaffoldViewModel = scaffoldViewModel,
                    bluetoothViewModel = bluetoothViewModel
                )
            },

            bottomBar = {
                IconButtonsBottomBar(
                    scaffoldViewModel = scaffoldViewModel
                )
            },

            content = { paddingValues ->
                ContentBottomBar(
                    context = context,
                    paddingValues = paddingValues,
                    bluetoothViewModel = bluetoothViewModel,
                    scaffoldViewModel = scaffoldViewModel,
                    parametersViewModel = parametersViewModel
                )
            }
        )
    }
}