package com.nicolas.maltesp.presentation.ui.navigation.screens.home.bottombar.content.conection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.presentation.viewmodels.BluetoothViewModel

@Composable
fun ConectionContent(
    bluetoothViewModel: BluetoothViewModel = hiltViewModel(),
) {
    val connectedDeviceName by bluetoothViewModel.connectedDeviceName.collectAsState()
    val memoryUsage by bluetoothViewModel.memoryUsage.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                if (connectedDeviceName != null) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 8.dp)
                            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f), shape = CircleShape)
                    )
                    Text(text = "Conectado: $connectedDeviceName", fontSize = 18.sp)
                } else {
                    Text(text = "Não conectado", fontSize = 18.sp)
                }
            }
            Text(text = "RAM: $memoryUsage")
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Button(onClick = {
                    if (!bluetoothViewModel.isConnected()){
                    bluetoothViewModel.connect()}
                }) { Text("Conectar") }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { bluetoothViewModel.disconnect() },
                    enabled = connectedDeviceName != null
                ) { Text("Desconectar") }

            }
        }
    }
}