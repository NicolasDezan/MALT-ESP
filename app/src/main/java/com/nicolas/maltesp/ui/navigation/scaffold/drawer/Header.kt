package com.nicolas.maltesp.ui.navigation.scaffold.drawer

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HeaderDrawer(){
    Surface (
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f)
    ){  }
}