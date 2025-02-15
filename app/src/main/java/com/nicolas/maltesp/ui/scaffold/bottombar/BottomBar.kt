package com.nicolas.maltesp.ui.scaffold.bottombar

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nicolas.maltesp.R
import com.nicolas.maltesp.ui.scaffold.bottombar.content.ConectionContent
import com.nicolas.maltesp.ui.scaffold.bottombar.content.ParametersInputContent
import com.nicolas.maltesp.ui.scaffold.bottombar.content.TestContent
import com.nicolas.maltesp.ui.theme.appcolors.ScaffoldColors
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.BottomBarViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel

const val ITEM_1 = "Conexão"
const val ITEM_2 = "Parâmetros"
const val ITEM_3 = "..."
const val ITEM_4 = "Leitura"

@Composable
fun IconButtonsBottomBar(bottomBarViewModel: BottomBarViewModel) {

    val selectedItem by bottomBarViewModel.selectedItem.collectAsState()

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
                onClick = { bottomBarViewModel.selectItem(index) },
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
    bottomBarViewModel: BottomBarViewModel,
    parametersViewModel: ParametersViewModel
) {
    val selectedItem by bottomBarViewModel.selectedItem.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (selectedItem) {
            0 -> ConectionContent(context = context, bluetoothViewModel = bluetoothViewModel)
            1 -> ParametersInputContent(parametersViewModel)
            2 -> TestContent()
            3 -> Text("4")
        }
    }
}