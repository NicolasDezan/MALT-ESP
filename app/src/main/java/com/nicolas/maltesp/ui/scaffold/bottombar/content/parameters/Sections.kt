package com.nicolas.maltesp.ui.scaffold.bottombar.content.parameters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nicolas.maltesp.data.dataclasses.ParameterData
import com.nicolas.maltesp.data.dataclasses.ParameterGroup
import com.nicolas.maltesp.data.dataclasses.ParameterSectionData
import com.nicolas.maltesp.viewmodels.ParametersViewModel

@Composable
fun sections(parametersViewModel: ParametersViewModel): List<ParameterSectionData> {
    val parametersState by parametersViewModel.parametersState.collectAsState()

    val sections = listOf(
        ParameterSectionData(
            title = "Maceração",
            groups = listOf(
                ParameterGroup(
                    parameters = listOf(
                        ParameterData(
                            "Tempo Submerso",
                            "h",
                            "",
                            parametersState.steeping.submergedTime
                        ),
                        ParameterData(
                            "Volume de Água",
                            "mL",
                            "",
                            parametersState.steeping.waterVolume
                        ),
                        ParameterData(
                            "Tempo Descanso",
                            "h",
                            "",
                            parametersState.steeping.restTime
                        ),
                        ParameterData(
                            "Número de Ciclos",
                            "",
                            "",
                            parametersState.steeping.cycles
                        )
                    )
                )
            )
        ),
        ParameterSectionData(
            title = "Germinação",
            groups = listOf(
                ParameterGroup(
                    parameters = listOf(
                        ParameterData(
                            "Nível de Rotação",
                            "",
                            "",
                            parametersState.germination.rotationLevel
                        ),
                        ParameterData(
                            "Tempo Total",
                            "h",
                            "",
                            parametersState.germination.totalTime
                        ),
                        ParameterData(
                            "Volume de Água",
                            "mL",
                            "",
                            parametersState.germination.waterVolume
                        ),
                        ParameterData(
                            "Adição de Água",
                            "min",
                            "",
                            parametersState.germination.waterAddition
                        )
                    )
                )
            )
        ),
        ParameterSectionData(
            title = "Secagem",
            groups = listOf(
                ParameterGroup(
                    parameters = listOf(
                        ParameterData(
                            "Temperatura",
                            "°C",
                            "",
                            parametersState.kilning.temperature
                        ),
                        ParameterData(
                            "Tempo",
                            "min",
                            "",
                            parametersState.kilning.time
                        )
                    )
                )
            )
        )
    )
    return sections
}