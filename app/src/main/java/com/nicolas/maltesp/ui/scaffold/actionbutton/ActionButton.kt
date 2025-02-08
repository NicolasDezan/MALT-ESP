package com.nicolas.maltesp.ui.scaffold.actionbutton

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nicolas.maltesp.R
import com.nicolas.maltesp.ui.theme.appcolors.ScaffoldColors

@Composable
fun SettingFloatingActionButton() {
    FloatingActionButton(
        containerColor = ScaffoldColors.ActionButton.ContainerColor,
        contentColor = ScaffoldColors.ActionButton.ContentColor,
        onClick = { /* Ação do botão flutuante */ }
    ) {
        Icon(
            ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
            contentDescription = "Editar"
        )
    }
}