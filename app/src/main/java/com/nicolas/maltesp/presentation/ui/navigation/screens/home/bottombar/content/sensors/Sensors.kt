package com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar.content.sensors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar.content.parameters.components.SectionTitle
import com.nicolas.maltesp.presentation.viewmodels.BluetoothViewModel

@Composable
fun SensorReadScreen(
    viewModel: BluetoothViewModel = hiltViewModel()
) {
    val sensorState by viewModel.sensorReadUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle(text = "Leitura de Sensores")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SensorReadBox(label = "Temperatura", value = sensorState.temperature)
            SensorReadBox(label = "Umidade", value = sensorState.humidity)
            SensorReadBox(label = "CO₂", value = sensorState.eco2)
        }

    }
}

@Composable
fun SensorReadBox(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = {
            Text(
                text = label,
                )
        },
        readOnly = true,
        enabled = false,
        modifier = Modifier
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.primary,
            disabledBorderColor = MaterialTheme.colorScheme.primary,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant)
    )
}