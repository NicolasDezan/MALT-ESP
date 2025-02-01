package com.nicolas.maltesp.screens

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nicolas.maltesp.R
import com.nicolas.maltesp.screens.bottombar.ContentBottomBar
import com.nicolas.maltesp.screens.bottombar.IconButtonsBottomBar
import com.nicolas.maltesp.ui.theme.appcolors.Colors
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.BottomBarViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun MainScreenWithDrawer(
    context: Context,
    bluetoothViewModel : BluetoothViewModel,
    bottomBarViewModel: BottomBarViewModel,
    parametersViewModel: ParametersViewModel
    ){

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { SettingDrawerContent() },
        content = {
            Scaffold(
                topBar = { SettingTopAppBar(scope, drawerState) },
                floatingActionButton = { SettingFloatingActionButton() },
                bottomBar = { IconButtonsBottomBar(bottomBarViewModel)},
                content = { paddingValues ->
                    ContentBottomBar(
                        context = context,
                        bluetoothViewModel = bluetoothViewModel,
                        bottomBarViewModel = bottomBarViewModel,
                        parametersViewModel = parametersViewModel,
                        paddingValues = paddingValues)
                }
            )
        }
    )
}

@Composable
fun SettingDrawerContent() {
    // Conteúdo do Drawer
    Text("Drawer Content")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopAppBar(scope: CoroutineScope, drawerState: DrawerState) {
    TopAppBar(
        title = { Text("ESP32 x Malteador") },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
                    contentDescription = "Menu"
                )
            }
        },
        colors = Colors.TopBar
    )
}

@Composable
fun SettingFloatingActionButton() {
    FloatingActionButton(onClick = { /* Ação do botão flutuante */ }) {
        Icon(
            ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
            contentDescription = "Editar"
        )
    }
}