package com.nicolas.maltesp.ui.theme.scaffold.bottombar.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nicolas.maltesp.R
import com.nicolas.maltesp.data.newRecipe
import com.nicolas.maltesp.viewmodels.ParametersViewModel
import kotlin.random.Random

@Composable
fun RecipesContent(parametersViewModel: ParametersViewModel){
    //Campo de TESTES//





    //Fim do Campo de Testes//



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        SelectRecipeDropdownMenu(parametersViewModel)
    }
}




@Composable
fun SelectRecipeDropdownMenu(parametersViewModel: ParametersViewModel){
    val expanded = remember { mutableStateOf(false) }
    val recipeNames by parametersViewModel.recipeNames.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    Row{
        Box {
            Button(onClick = {
                parametersViewModel.refreshRecipeNames()
                expanded.value = true
            }) {
                Text("Abrir Menu")
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                recipeNames.forEach { recipeName ->
                    DropdownMenuItem(
                        text = { Row {
                            Text(text = recipeName)
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            IconButton(onClick = {/*TODO: APAGAR e Centralizar essa merda*/}) { Icon(imageVector = ImageVector.vectorResource(R.drawable.baseline_delete_24), contentDescription = "") }
                        }
                               },
                        onClick = {
                            parametersViewModel.loadRecipeByName(recipeName)
                            expanded.value = false
                        }
                    )
                }

            }
        }

        Button(
            onClick = {
                showDialog.value = true // Atualiza o estado para mostrar o diálogo
            }
        ) {
            Text("Salvar Receita")
        }

        // Exibe o diálogo apenas se o estado for true
        if (showDialog.value) {
            NameInputDialog(
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
}

@Composable
fun NameInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Digite o Nome") },
        text = {
            Column {
                Text("Por favor, insira um nome abaixo:")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("Nome") },
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


