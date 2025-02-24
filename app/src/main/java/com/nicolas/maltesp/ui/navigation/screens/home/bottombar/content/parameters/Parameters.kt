package com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters.components.GroupSubtitle
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters.components.NewRecipeButton
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters.components.ParameterFieldInput
import com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters.components.SectionTitle
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel


@Composable
fun ParametersContent(
    parametersViewModel: ParametersViewModel,
    bluetoothViewModel: BluetoothViewModel,
) {
    val parameterSectionData = parameterSectionData(parametersViewModel, bluetoothViewModel)

    Column {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp))
        {
            items(parameterSectionData) { section ->
                ParameterSection(
                    title = section.title,
                    groups = section.groups,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
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
    groups: List<ParameterGroup>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionTitle(text = title)
        Spacer(modifier = Modifier.height(4.dp))
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
                group.parameters.forEach { parameterData ->
                    ParameterFieldInput(
                        parameterData = parameterData
                    )
                }
            }
        }
    }
}