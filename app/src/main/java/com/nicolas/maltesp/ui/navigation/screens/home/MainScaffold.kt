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

@Composable
fun MainScaffold(
    context: Context,
    bluetoothViewModel : BluetoothViewModel,
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
                bluetoothViewModel = bluetoothViewModel
            ) },

            floatingActionButton = {
                SettingFloatingActionButton(
                    context = context,
                    bluetoothViewModel = bluetoothViewModel
                )
            },

            bottomBar = {
                IconButtonsBottomBar(
                )
            },

            content = { paddingValues ->
                ContentBottomBar(
                    context = context,
                    paddingValues = paddingValues,
                    bluetoothViewModel = bluetoothViewModel,
                )
            }
        )
    }
}