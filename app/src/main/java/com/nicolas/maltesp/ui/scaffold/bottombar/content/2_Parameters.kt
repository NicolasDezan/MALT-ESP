package com.nicolas.maltesp.ui.scaffold.bottombar.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolas.maltesp.data.dataclasses.ParameterGroup
import com.nicolas.maltesp.data.newRecipe
import com.nicolas.maltesp.ui.scaffold.bottombar.content.parameters.sections
import com.nicolas.maltesp.ui.theme.appcolors.ComponentsColors
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import kotlin.random.Random


@Composable
fun ParametersInputContent(
    parametersViewModel: ParametersViewModel,
    bluetoothViewModel: BluetoothViewModel
) {
    val sections = sections(parametersViewModel, bluetoothViewModel)

    Column {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp))
        {
            items(sections) { section ->
                ParameterSection(
                    title = section.title,
                    groups = section.groups
                )
            }
            item { // Divisão pra nao atrapalhar a leitura do ultimo section.item
                Surface(
                    modifier = Modifier.height((86).dp).fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start

                    ) {
                        HorizontalDivider()

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 24.dp),
                        ) {
                            NewRecipeButton(
                                textButton = "Salvar Nova Receita",
                                parametersViewModel = parametersViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ParameterSection(
    title: String,
    groups: List<ParameterGroup>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionTitle(text = title)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            groups.forEach { group ->
                if (group.title != null) {
                    GroupSubtitle(text = group.title)
                }
                group.parameters.forEach { parameter ->
                    ParameterInput(
                        parameterName = parameter.name,
                        unit = parameter.unit,
                        description = parameter.description,
                        inputState = parameter.state,
                        isEquals = parameter.isEquals
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color= MaterialTheme.colorScheme.secondary)
            .padding(top = 4.dp, bottom = 4.dp)
    )
}

@Composable
private fun GroupSubtitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun ParameterInput(
    parameterName: String,
    unit: String,
    description: String,
    inputState: MutableState<String>,
    isEquals: Boolean?
) {

    val textFieldColor = when (isEquals){
        true -> ComponentsColors.TextField.equalState
        false -> ComponentsColors.TextField.notEqualState
        null -> ComponentsColors.TextField.invalidState
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Coluna para textos alinhados à esquerda
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = if (unit.isEmpty()) parameterName else "$parameterName ($unit)",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )

            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                    modifier = Modifier.offset(y = (-4).dp)
                )
            }
        }

        Box(
            modifier = Modifier.width(120.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            TextField(
                value = inputState.value,
                onValueChange = { newValue ->
                    val regex = Regex("^\\d{0,7}(\\.\\d{0,6})?$")

                    if (newValue.isEmpty()) {
                        inputState.value = ""
                    }

                    // Corrige "." inicial
                    val correctedValue = if (newValue.startsWith(".")) "0$newValue" else newValue

                    // Remove zeros à esquerda
                    val sanitizedValue = if (correctedValue.startsWith("0")
                        && correctedValue != "0"
                        && !correctedValue.startsWith("0.")) {
                        correctedValue.replaceFirst("^0+(?!$)".toRegex(), "")
                    } else {
                        correctedValue
                    }

                    // Aplica validação
                    if (sanitizedValue.matches(regex)
                        && sanitizedValue.length <= 7
                        && !sanitizedValue.startsWith(".")) {
                        inputState.value = sanitizedValue
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .width(100.dp)
                    .height(48.dp),
                colors = textFieldColor
            )
        }
    }
}

@Composable
fun NewRecipeButton(textButton: String, parametersViewModel: ParametersViewModel){
    val showDialog = remember { mutableStateOf(false) }
    Button(
        onClick = {
            showDialog.value = true // Atualiza o estado para mostrar o diálogo
        }
    ) {
        Text(textButton)
    }

    // Exibe o diálogo apenas se o estado for true
    if (showDialog.value) {
        StringInputDialog(
            title = "Digite o Nome",
            label = "Os parâmetros atuais serão salvos com esse nome.",
            placeholder = "Nome",
            onDismiss = { showDialog.value = false },
            onConfirm = { inputRecipeName ->
                val recipe = newRecipe(
                    uid = Random.nextInt(1, Int.MAX_VALUE),
                    recipeName = inputRecipeName,
                    parametersState = parametersViewModel.parametersState.value
                )
                parametersViewModel.saveRecipe(recipe)
                showDialog.value = false
            }
        )
    }
}

@Composable
fun StringInputDialog(
    title: String = "Digite o Nome",
    label: String = "Por favor, insira um nome abaixo:",
    placeholder: String = "Nome",
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                Text(label)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text(placeholder) },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(text) }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
