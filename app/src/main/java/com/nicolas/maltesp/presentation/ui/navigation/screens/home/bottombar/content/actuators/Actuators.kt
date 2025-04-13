package com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar.content.actuators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.presentation.viewmodels.BluetoothViewModel

@Composable
fun ActuatorStatusScreen(
    viewModel: BluetoothViewModel = hiltViewModel()
) {
    val actuatorState by viewModel.actuatorUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Estado dos Atuadores",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ActuatorItem("Válvula de Entrada", actuatorState.entrada)
        ActuatorItem("Válvula de Saída", actuatorState.saida)
        ActuatorItem("Rotação", actuatorState.rotacao)
        ActuatorItem("Resistência", actuatorState.resistencia)
        ActuatorItem("Bomba de Ar", actuatorState.bombaAr)
    }
}

@Composable
fun ActuatorItem(
    label: String,
    state: String
) {
    val (color, text) = when (state) {
        "Ligado" -> MaterialTheme.colorScheme.primary to "ON"
        "Desligado" -> MaterialTheme.colorScheme.tertiary to "OFF"
        else -> MaterialTheme.colorScheme.outline to "—"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
