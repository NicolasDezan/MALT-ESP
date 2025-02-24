package com.nicolas.maltesp.ui.navigation.screens.home.bottombar

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nicolas.maltesp.R
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.conection.ConectionContent
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters.ParametersContent
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.test.TestContent
import com.nicolas.maltesp.ui.theme.appcolors.ScaffoldColors
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ScaffoldViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import com.nicolas.maltesp.viewmodels.SettingsViewModel

const val ITEM_1 = "Conexão"
const val ITEM_2 = "Parâmetros"
const val ITEM_3 = "..."
const val ITEM_4 = "Leitura"

@Composable
fun IconButtonsBottomBar(scaffoldViewModel: ScaffoldViewModel) {
    val selectedItem by scaffoldViewModel.bottomBarSelectedItem.collectAsState()
    val items = listOf(ITEM_1, ITEM_2, ITEM_3, ITEM_4)

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
                onClick = { scaffoldViewModel.bottomBarSelectItem(index) },
                colors = ScaffoldColors.BottomBar
            )
        }
    }
}

@Composable
fun ContentBottomBar(
    context: Context,
    paddingValues: PaddingValues,
    bluetoothViewModel: BluetoothViewModel,
    scaffoldViewModel: ScaffoldViewModel,
    parametersViewModel: ParametersViewModel,
    settingsViewModel: SettingsViewModel
) {
    val selectedItem by scaffoldViewModel.bottomBarSelectedItem.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (selectedItem) {
            0 -> ContentBox(scaffoldViewModel) { ConectionContent(context = context, bluetoothViewModel = bluetoothViewModel, settingsViewModel = settingsViewModel) }
            1 -> ContentBox(scaffoldViewModel) { ParametersContent(parametersViewModel = parametersViewModel, bluetoothViewModel = bluetoothViewModel) }
            2 -> ContentBox(scaffoldViewModel) { TestContent() }
            3 -> ContentBox(scaffoldViewModel) { TestContent() }
        }
    }
}

@Composable
fun ContentBox(
    scaffoldViewModel: ScaffoldViewModel,
    content: @Composable () -> Unit
){
    val isFabExpanded by scaffoldViewModel.isFabExpanded.collectAsState()

    Box{
        content()
        if (isFabExpanded) {
            Surface(
                color = Color.Black.copy(alpha = 0.035f), // Cor semi-transparente
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        scaffoldViewModel.toggleFab()
                    }
            ) {}
        }
    }
}