package com.nicolas.maltesp.screens.bottomcontent

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConectionContent(
    onConnect: () -> Unit,
    onDisconnect: () -> Unit,
    onSendCommand: (String) -> Unit,
    onSendCommandArrayFloat: (ByteArray) -> Unit,
    temperature: String,
    connectedDeviceName: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
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
                            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                    )
                    Text(text = "Conectado: $connectedDeviceName", fontSize = 18.sp)
                } else {
                    Text(text = "Não conectado", fontSize = 18.sp)
                }
            }
            Text("Temperatura: $temperature °C", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = { onConnect() }) { Text("Conectar") }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { onDisconnect() },
                    enabled = connectedDeviceName != null
                ) { Text("Desconectar") }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onSendCommand("ON") }) { Text("Ligar LED") }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onSendCommand("OFF") }) { Text("Desligar LED") }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val byteArray = byteArrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)
                onSendCommandArrayFloat(byteArray)
            }) { Text("Enviar array de float") }

        }
    }
}