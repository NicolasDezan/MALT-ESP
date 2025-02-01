package com.nicolas.maltesp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nicolas.maltesp.R
import com.nicolas.maltesp.screens.bottomcontent.ConectionContent
import com.nicolas.maltesp.screens.bottomcontent.ParametersInputContent
import com.nicolas.maltesp.screens.bottomcontent.RecipesContent
import com.nicolas.maltesp.viewmodels.BottomBarViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val ITEM_1 = "Conexão"
const val ITEM_2 = "Parâmetros"
const val ITEM_3 = "..."
const val ITEM_4 = "Leitura"

@Composable
fun MainScreenWithDrawer(
    onConnect: () -> Unit,
    onDisconnect: () -> Unit,
    onSendCommand: (String) -> Unit,
    onSendCommandArrayFloat: (ByteArray) -> Unit,
    temperature: String,
    connectedDeviceName: String?,
    bottomBarViewModel: BottomBarViewModel,
    parametersViewModel: ParametersViewModel
    ){

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val items = listOf(ITEM_1, ITEM_2, ITEM_3, ITEM_4)

    val selectedItem by bottomBarViewModel.selectedItem.collectAsState()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { SettingDrawerContent() },
        content = {
            Scaffold(
                topBar = { SettingTopAppBar(scope, drawerState) },
                floatingActionButton = { SettingFloatingActionButton() },
                bottomBar = { IconButtonsBottomBar(items, selectedItem) { bottomBarViewModel.selectItem(it) } },
                content = { paddingValues ->
                    ContentBottomBar(paddingValues, selectedItem, onConnect, onDisconnect, onSendCommand, onSendCommandArrayFloat, temperature, connectedDeviceName, parametersViewModel)
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
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

@Composable
fun IconButtonsBottomBar(items: List<String>, selectedItem: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    val iconId = when (item) {
                        ITEM_1 -> R.drawable.baseline_bluetooth
                        ITEM_2 -> R.drawable.baseline_menu_24
                        ITEM_3 -> R.drawable.baseline_menu_24
                        ITEM_4 -> R.drawable.baseline_menu_24
                        else -> {
                            R.drawable.baseline_menu_24
                        }
                    }
                    Icon(
                        ImageVector.vectorResource(id = iconId),
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

@Composable
fun ContentBottomBar(paddingValues: PaddingValues, selectedItem: Int, onConnect: () -> Unit, onDisconnect: () -> Unit, onSendCommand: (String) -> Unit, onSendCommandArrayFloat: (ByteArray) -> Unit, temperature: String, connectedDeviceName: String?, parametersViewModel: ParametersViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (selectedItem) {
            0 -> ConectionContent(
                onConnect = onConnect, onDisconnect = onDisconnect, onSendCommand = onSendCommand, temperature = temperature,
                onSendCommandArrayFloat = onSendCommandArrayFloat ,
                connectedDeviceName = connectedDeviceName)
            1 -> ParametersInputContent(parametersViewModel)
            2 -> RecipesContent(parametersViewModel)
            3 -> Text("4")
        }
    }
}
