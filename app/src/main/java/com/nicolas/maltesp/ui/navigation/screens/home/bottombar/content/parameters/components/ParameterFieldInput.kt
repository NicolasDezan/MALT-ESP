package com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters.ParameterData
import com.nicolas.maltesp.ui.theme.appcolors.ComponentsColors
import java.util.Locale

@Composable
fun ParameterFieldInput(parameterData: ParameterData) {
    val textFieldColor = when (parameterData.isEquals){
        true -> ComponentsColors.TextField.equalState
        false -> ComponentsColors.TextField.notEqualState
        null -> ComponentsColors.TextField.invalidState // -> Estado desconectado
    }

    var isError = false
    if(parameterData.isEquals != null) {
        isError = !parameterData.isNumberValid
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = if (parameterData.unit.isEmpty()) parameterData.name else "${parameterData.name} (${parameterData.unit})",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )

            if (parameterData.description.isNotEmpty()) {
                Text(
                    text = parameterData.description,
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
                value = parameterData.state.value,
                onValueChange = { newValue ->
                    val regex = Regex("^\\d{0,7}(\\.\\d{0,6})?$")

                    if (newValue.isEmpty()) {
                        parameterData.state.value = ""
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
                        parameterData.state.value = sanitizedValue
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
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = if (isError) MaterialTheme.colorScheme.error else Color.Transparent
                    ),
                colors = textFieldColor,
            )
            if(isError) {
                Column(
                    modifier = Modifier
                        .offset(y = 40.dp),
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = "Valor inválido",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = "Faixa: ${String.format(Locale.US, parameterData.numerFormat, parameterData.range.min)} - ${String.format(Locale.US, parameterData.numerFormat, parameterData.range.max)}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp),
                        modifier = Modifier
                            .offset(y = (-6).dp),
                        maxLines = 1,
                        textAlign = TextAlign.End
                    )
                }

            }
        }
    }
}