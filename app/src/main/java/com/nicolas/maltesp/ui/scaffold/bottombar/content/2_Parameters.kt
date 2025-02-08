package com.nicolas.maltesp.ui.scaffold.bottombar.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolas.maltesp.viewmodels.ParametersViewModel

@Composable
fun ParametersInputContent(parametersViewModel: ParametersViewModel) {
    LazyColumn {
        item {
            ParameterSteeping(parametersViewModel)
            Spacer(modifier = Modifier.height(8.dp))
            ParameterGermination(parametersViewModel)
            Spacer(modifier = Modifier.height(8.dp))
            ParameterKilning(parametersViewModel)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ParameterSteeping(parametersViewModel: ParametersViewModel){
    val parametersState by parametersViewModel.parametersState.collectAsState()

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            "Maceração",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                //.background(color= MaterialTheme.colorScheme.tertiaryContainer)
                .padding(top = 4.dp, bottom = 4.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ParameterInput("Tempo Submerso", "h", "por ciclo",parametersState.steeping.submergedTime)
            ParameterInput("Volume de Água", "mL", "por ciclo",parametersState.steeping.waterVolume)
            ParameterInput("Tempo Descanso", "h", "por ciclo",parametersState.steeping.restTime)
            ParameterInput("Número de Ciclos", "", "",parametersState.steeping.cycles)
        }
    }
}

@Composable
fun ParameterGermination(parametersViewModel: ParametersViewModel){
    val parametersState by parametersViewModel.parametersState.collectAsState()

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            "Germinação",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                //.background(color= MaterialTheme.colorScheme.tertiaryContainer)
                .padding(top = 4.dp, bottom = 4.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ParameterInput("Nível de Rotação", "", "",parametersState.germination.rotationLevel)
            ParameterInput("Tempo Total", "h", "",parametersState.germination.totalTime)

            Text(
                text = "Controle Umidade",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    //.background(color= MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(top = 3.dp, bottom = 3.dp),
                textAlign = TextAlign.Center
            )

            ParameterInput("Volume de Água", "mL", "por ciclo",parametersState.germination.waterVolume)
            ParameterInput("Adição de Água", "min", "Tempo a cada fornecimento de água",parametersState.germination.waterAddition)
        }
    }
}

@Composable
fun ParameterKilning(parametersViewModel: ParametersViewModel){
    val parametersState by parametersViewModel.parametersState.collectAsState()

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            "Secagem",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                //.background(color= MaterialTheme.colorScheme.tertiaryContainer)
                .padding(top = 4.dp, bottom = 4.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ParameterInput("Temperatura", "°C", "", parametersState.kilning.temperature)
            ParameterInput("Tempo", "min", "", parametersState.kilning.time)
        }
    }
}



@Composable
fun ParameterInput(
    parameterName: String,
    unit: String,
    description: String,
    inputState: MutableState<String> // Recebe o estado de fora
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (unit == "") parameterName else "$parameterName ($unit)",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
        )

        if (description.isNotEmpty()) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.offset(y = (-6).dp)
            )
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }

        TextField(
            value = inputState.value,
            onValueChange = { newValue ->
                // Permitir apenas números e um único ponto (para decimais)
                if (newValue.isEmpty() || newValue.all { it.isDigit() || it == '.' } && newValue.count { it == '.' } <= 1) {
                    inputState.value = newValue
                }
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .size(width = 80.dp, height = 50.dp)
                .offset(y = (-4).dp),
            //colors = TextFieldDefaults.colors()
        )
    }
}

