package com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.data.newRecipe
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import com.nicolas.maltesp.viewmodels.RecipesViewModel
import kotlin.random.Random

@Composable
fun NewRecipeButton(
    textButton: String,
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    parametersViewModel: ParametersViewModel = hiltViewModel()
){
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Button(
        onClick = {
            showDialog.value = true
        }
    ) {
        Text(textButton)
    }

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
                recipesViewModel.saveRecipe(recipe)
                showDialog.value = false
                Toast.makeText(context, "A receita '$inputRecipeName' foi salva", Toast.LENGTH_SHORT).show()
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
    onConfirm: (String) -> Unit,

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
            Button(onClick = {
                onConfirm(text)
            }) {
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