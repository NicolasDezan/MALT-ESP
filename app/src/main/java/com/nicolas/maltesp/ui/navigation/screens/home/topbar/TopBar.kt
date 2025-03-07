package com.nicolas.maltesp.ui.navigation.screens.home.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.others.objects.VectorIcons
import com.nicolas.maltesp.ui.theme.appcolors.ScaffoldColors
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ScaffoldViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopAppBar(scope: CoroutineScope,
                     drawerState: DrawerState,
) {
    TopAppBar(
        title = { TobBarTitle() },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    ImageVector.vectorResource(id = VectorIcons.menu),
                    contentDescription = "Menu"
                )
            }
        },
        colors = ScaffoldColors.TopBar
    )
}

@Composable
fun TobBarTitle(
    scaffoldViewModel: ScaffoldViewModel = hiltViewModel(),
    ) {
    val selectedItem by scaffoldViewModel.bottomBarSelectedItem.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            if (selectedItem == 1) {
                Text(text = "Parâmetros")
            } else {
                Text(text = "ESP32 x Malteador")
            }
            ConnectionIndicator()
        }

}