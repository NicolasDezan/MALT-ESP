package com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.presentation.ui.icons.VectorIcons
import com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar.content.actuators.ActuatorStatusScreen
import com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar.content.conection.ConectionContent
import com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar.content.parameters.ParametersContent
import com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar.content.sensors.SensorReadScreen
import com.nicolas.maltesp.presentation.ui.theme.appcolors.ScaffoldColors
import com.nicolas.maltesp.presentation.viewmodels.ScaffoldViewModel

const val ITEM_1 = "Conexão"
const val ITEM_2 = "Parâmetros"
const val ITEM_3 = "Sensores"
const val ITEM_4 = "Atuadores"


@Composable
fun IconButtonsBottomBar(
    scaffoldViewModel: ScaffoldViewModel = hiltViewModel()
    ) {
    val selectedItem by scaffoldViewModel.bottomBarSelectedItem.collectAsState()
    val items = listOf(ITEM_1, ITEM_2, ITEM_3, ITEM_4)

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    val iconId = when (item) {
                        ITEM_1 -> VectorIcons.bluetooth
                        ITEM_2 -> VectorIcons.parameters
                        ITEM_3 -> VectorIcons.sensors
                        ITEM_4 -> VectorIcons.actuators
                        else -> {
                            VectorIcons.menu
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
    paddingValues: PaddingValues,
    scaffoldViewModel: ScaffoldViewModel = hiltViewModel(),
) {
    val selectedItem by scaffoldViewModel.bottomBarSelectedItem.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (selectedItem) {
            0 -> ContentBox { ConectionContent() }
            1 -> ContentBox { ParametersContent() }
            2 -> ContentBox { SensorReadScreen() }
            3 -> ContentBox { ActuatorStatusScreen() }
        }
    }
}

@Composable
fun ContentBox(
    scaffoldViewModel: ScaffoldViewModel = hiltViewModel(),
    content: @Composable () -> Unit
){
    val isFabExpanded by scaffoldViewModel.isFabExpanded.collectAsState()

    Box{
        content()
        if (isFabExpanded) {
            Surface(
                color = Color.Black.copy(alpha = 0.035f),
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