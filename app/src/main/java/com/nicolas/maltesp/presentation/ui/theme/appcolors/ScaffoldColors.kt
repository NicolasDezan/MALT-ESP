package com.nicolas.maltesp.presentation.ui.theme.appcolors

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object ScaffoldColors {
    @OptIn(ExperimentalMaterial3Api::class)
    val TopBar: TopAppBarColors
        @Composable
        get() = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )

    val BottomBar: NavigationBarItemColors
        @Composable
        get() = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.secondary,
        )

    object ActionButton {
        val ContainerColor: Color
            @Composable
            get() = MaterialTheme.colorScheme.primary
        val ContentColor: Color
            @Composable
            get() = MaterialTheme.colorScheme.onPrimary
    }



}