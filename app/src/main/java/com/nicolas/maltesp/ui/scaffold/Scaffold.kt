package com.nicolas.maltesp.ui.scaffold

import android.content.Context
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.nicolas.maltesp.ui.scaffold.actionbutton.SettingFloatingActionButton
import com.nicolas.maltesp.ui.scaffold.bottombar.ContentBottomBar
import com.nicolas.maltesp.ui.scaffold.bottombar.IconButtonsBottomBar
import com.nicolas.maltesp.ui.scaffold.drawer.SettingDrawerContent
import com.nicolas.maltesp.ui.scaffold.topbar.SettingTopAppBar
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ScaffoldViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel


@Composable
fun ScaffoldWithDrawer(
    context: Context,
    bluetoothViewModel : BluetoothViewModel,
    scaffoldViewModel: ScaffoldViewModel,
    parametersViewModel: ParametersViewModel
    ){

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            SettingDrawerContent()
                        },

        content = {
            Scaffold(
                topBar = { SettingTopAppBar(
                    scope = scope,
                    drawerState = drawerState,
                    scaffoldViewModel = scaffoldViewModel) },

                floatingActionButton = {
                    SettingFloatingActionButton(
                        parametersViewModel = parametersViewModel,
                        scaffoldViewModel = scaffoldViewModel
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
    )
}