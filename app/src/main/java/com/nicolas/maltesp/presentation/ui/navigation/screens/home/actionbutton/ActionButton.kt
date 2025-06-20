package com.nicolas.maltesp.presentation.ui.navigation.screens.home.actionbutton

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolas.maltesp.data.local.entities.newRecipe
import com.nicolas.maltesp.presentation.ui.icons.VectorIcons
import com.nicolas.maltesp.presentation.ui.theme.appcolors.ScaffoldColors
import com.nicolas.maltesp.presentation.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.presentation.viewmodels.ParametersViewModel
import com.nicolas.maltesp.presentation.viewmodels.RecipesViewModel
import com.nicolas.maltesp.presentation.viewmodels.ScaffoldViewModel

/*
    Créditos: https://github.com/MohsenMashkour/ExpandableFabComposeYT.git
    /\ A base desse arquivo foi construída a partir desse repositório /\
*/

data class ActionItem(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)

@Composable
fun SettingFloatingActionButton(
    scaffoldViewModel: ScaffoldViewModel = hiltViewModel(),
    parametersViewModel: ParametersViewModel = hiltViewModel(),
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    bluetoothViewModel: BluetoothViewModel = hiltViewModel(),
) {
    val isFabExpanded by scaffoldViewModel.isFabExpanded.collectAsState()
    var showSelectRecipeList by remember { mutableStateOf(false) }

    val fabMenuItems = listOf(
        ActionItem(
            icon = ImageVector.vectorResource(id = VectorIcons.bluetoothInteraction),
            title = "Puxar Parâmetros",
            onClick = {
                if (bluetoothViewModel.isReadToPullParameters()) {
                    bluetoothViewModel.sendCommandArray(byteArray = byteArrayOf(-127))
                    scaffoldViewModel.toggleFab()
                }else {
                    //Toast.makeText(context, "Erro ao puxar os parâmetros. Verifique a conexão", Toast.LENGTH_SHORT).show()
                }
            }
        ),
        ActionItem(
            icon = ImageVector.vectorResource(id = VectorIcons.loadRecipe),
            title = "Carregar Receita",
            onClick = {
                showSelectRecipeList = true
                scaffoldViewModel.toggleFab()
            }
        ),
        ActionItem(
            icon = ImageVector.vectorResource(id = VectorIcons.play),
            title = "Iniciar",
            onClick = {
                if (parametersViewModel.isAllParametersValid() && bluetoothViewModel.isConnected()) {
                    try {
                        val parametersByteArray = parametersViewModel.parametersToByteArray()
                        val startProcess = byteArrayOf(50)

                        bluetoothViewModel.sendCommandArray(
                            byteArray = parametersByteArray
                                ?: throw IllegalArgumentException("byteArray returned null")
                        )
                        println("[DEBUG] Parâmetros Enviados: ${parametersByteArray.contentToString()}")

                        Thread.sleep(100)

                        bluetoothViewModel.sendCommandArray(
                            byteArray = startProcess
                        )

                        println("[DEBUG] Comando de Iniciar processo enviado: ${startProcess.contentToString()}")
                        recipesViewModel.saveRecipe(
                            newRecipe(
                                uid = 10,
                                recipeName = "Última Enviada",
                                parametersViewModel.parametersState.value
                            )
                        )
                        println("[DEBUG] Receita salva como 'Última Enviada'")
                        scaffoldViewModel.toggleFab()
                    } catch (e: IllegalArgumentException) {
                        println("[ERROR] Erro ao clicar em INICIAR: $e")
                    }
                }
            }
        )
    )

    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = isFabExpanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            LazyColumn(
                modifier = Modifier.padding(bottom = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                items(fabMenuItems.size) {
                    ItemUi(
                        icon = fabMenuItems[it].icon,
                        title = fabMenuItems[it].title,
                        onClick = fabMenuItems[it].onClick
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        val transition = updateTransition(targetState = isFabExpanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = { scaffoldViewModel.toggleFab() },
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
            onDismiss = { showSelectRecipeList = false },
        )
    }
}

@Composable
fun ItemUi(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
    )
    {
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



