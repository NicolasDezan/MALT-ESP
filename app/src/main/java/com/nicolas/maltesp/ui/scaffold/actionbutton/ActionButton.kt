package com.nicolas.maltesp.ui.scaffold.actionbutton

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nicolas.maltesp.R
import com.nicolas.maltesp.ui.theme.appcolors.ScaffoldColors
import com.nicolas.maltesp.viewmodels.ParametersViewModel

/*
    Créditos: https://github.com/MohsenMashkour/ExpandableFabComposeYT.git
    /\ A base desse arquivo foi construída a partir desse repositório /\
*/

@Composable
fun SettingFloatingActionButton(parametersViewModel: ParametersViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var showSelectRecipeList by remember { mutableStateOf(false) }

    val items = listOf(
        ActionItem(
            icon = ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
            title = "Carregar Receita",
            onClick = { showSelectRecipeList = true }
        ),
        ActionItem(
            icon = ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
            title = "Iniciar",
            onClick = { /* TODO: Um dia vamos colocar o envio de dados pro ESP aqui */ }
        )
    )

    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            LazyColumn(Modifier.padding(bottom = 8.dp)) {
                items(items.size) {
                    ItemUi(
                        icon = items[it].icon,
                        title = items[it].title,
                        onClick = items[it].onClick
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        val transition = updateTransition(targetState = expanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = ScaffoldColors.ActionButton.ContainerColor,
            contentColor = ScaffoldColors.ActionButton.ContentColor
        ) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "",
                modifier = Modifier.rotate(rotation)
            )
        }
    }

    if (showSelectRecipeList) {
        SelectRecipeDialog(
            parametersViewModel = parametersViewModel,
            onDismiss = { showSelectRecipeList = false }
        )
    }
}

@Composable
fun ItemUi(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .border(2.dp, ScaffoldColors.ActionButton.ContainerColor, RoundedCornerShape(10.dp))
                .background(color = Color.White, RoundedCornerShape(10.dp))
                .padding(6.dp)

        ) {
            Text(
                text = title
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(45.dp),
            containerColor = ScaffoldColors.ActionButton.ContainerColor,
            contentColor = ScaffoldColors.ActionButton.ContentColor
        ) {
            Icon(imageVector = icon, contentDescription = "")
        }
    }
}

data class ActionItem(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit // Nova propriedade
)

@Composable
fun SelectRecipeDialog(
    parametersViewModel: ParametersViewModel,
    onDismiss: () -> Unit
) {
    val recipeNames by parametersViewModel.recipeNames.collectAsState()
    parametersViewModel.refreshRecipeNames()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selecione uma receita") },
        text = {
            LazyColumn { item{
                recipeNames.forEach { recipeName ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                parametersViewModel.loadRecipeByName(recipeName)
                                onDismiss()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = recipeName
                        )
                        IconButton(onClick = {
                            parametersViewModel.deleteRecipeByName(recipeName)
                            onDismiss()
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_delete_24),
                                contentDescription = "Excluir"
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }}
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}