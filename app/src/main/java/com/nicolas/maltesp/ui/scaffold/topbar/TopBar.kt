package com.nicolas.maltesp.ui.scaffold.topbar

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nicolas.maltesp.R
import com.nicolas.maltesp.ui.theme.appcolors.ScaffoldColors
import com.nicolas.maltesp.viewmodels.ScaffoldViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopAppBar(scope: CoroutineScope,
                     drawerState: DrawerState,
                     scaffoldViewModel: ScaffoldViewModel
) {
    TopAppBar(
        title = { TobBarTitle(scaffoldViewModel) },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
                    contentDescription = "Menu"
                )
            }
        },
        colors = ScaffoldColors.TopBar
    )
}

@Composable
fun TobBarTitle(scaffoldViewModel: ScaffoldViewModel){
    val selectedItem by scaffoldViewModel.bottomBarSelectedItem.collectAsState()

    if(selectedItem == 1){
        Text(text = "Parâmetros")
    }else{
        Text(text = "ESP32 x Malteador")
    }
}