package com.nicolas.maltesp.ui.navigation.screens.home.topbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.viewmodels.BluetoothViewModel

@Composable
fun ConnectionIndicator(
    bluetoothViewModel: BluetoothViewModel = hiltViewModel()
){
    val pulseConnection: Boolean = bluetoothViewModel.pulseConnection.collectAsState().value

    Surface(
        modifier = Modifier.size(26.dp).border(color = Color.LightGray, width = 0.25.dp, shape = CircleShape),
        shape = CircleShape,
        color = if (pulseConnection) MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    else Color.LightGray.copy(alpha = 0.5f)
    ) {}
}