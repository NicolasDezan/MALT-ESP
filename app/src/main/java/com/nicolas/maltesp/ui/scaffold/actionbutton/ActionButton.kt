package com.nicolas.maltesp.ui.scaffold.actionbutton

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nicolas.maltesp.R

@Composable
fun SettingFloatingActionButton() {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.background,
        onClick = { /* Ação do botão flutuante */ }
    ) {
        Icon(
            ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
            contentDescription = "Editar"
        )
    }
}