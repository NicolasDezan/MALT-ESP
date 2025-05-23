package com.nicolas.maltesp.presentation.ui.navigation.screens.home.actionbutton

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.presentation.ui.icons.VectorIcons
import com.nicolas.maltesp.presentation.viewmodels.RecipesViewModel

@Composable
fun SelectRecipeDialog(
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {
    val recipeNames by recipesViewModel.recipeNames.collectAsState()
    recipesViewModel.refreshRecipeNames()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selecione uma receita") },
        text = {
            LazyColumn {
                item {
                    recipeNames.forEach { recipeName ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    recipesViewModel.loadRecipeByName(recipeName)
                                    onDismiss()
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = recipeName
                            )
                            IconButton(onClick = {
                                recipesViewModel.deleteRecipeByName(recipeName)
                                onDismiss()
                                //Toast.makeText(context, "A receita '$recipeName' foi excluída", Toast.LENGTH_SHORT).show()
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(VectorIcons.thrashDelete),
                                    contentDescription = "Excluir"
                                )
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}