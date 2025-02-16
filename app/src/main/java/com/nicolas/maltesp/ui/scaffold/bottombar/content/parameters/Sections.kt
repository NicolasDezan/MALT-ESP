package com.nicolas.maltesp.ui.scaffold.bottombar.content.parameters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nicolas.maltesp.data.dataclasses.ParameterData
import com.nicolas.maltesp.data.dataclasses.ParameterGroup
import com.nicolas.maltesp.data.dataclasses.ParameterSectionData
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel

@Composable
fun sections(parametersViewModel: ParametersViewModel, bluetoothViewModel: BluetoothViewModel): List<ParameterSectionData> {
    val parametersState by parametersViewModel.parametersState.collectAsState()
    val parametersReceived by bluetoothViewModel.parametersReceived.collectAsState()
    val isBluetoothConnected = bluetoothViewModel.isConnected()

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
                            parametersState.steeping.submergedTime,
                            parametersViewModel.compareSteepingSubmergedTime(parametersReceived, isBluetoothConnected)
                        ),
                        ParameterData(
                            "Volume de Água",
                            "mL",
                            "",
                            parametersState.steeping.waterVolume,
                            parametersViewModel.compareSteepingWaterVolume(parametersReceived, isBluetoothConnected)
                        ),
                        ParameterData(
                            "Tempo Descanso",
                            "h",
                            "",
                            parametersState.steeping.restTime,
                            parametersViewModel.compareSteepingRestTime(parametersReceived, isBluetoothConnected)
                        ),
                        ParameterData(
                            "Número de Ciclos",
                            "",
                            "",
                            parametersState.steeping.cycles,
                            parametersViewModel.compareSteepingCycles(parametersReceived, isBluetoothConnected)
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
                            parametersState.germination.rotationLevel,
                            parametersViewModel.compareGerminationRotationLevel(parametersReceived, isBluetoothConnected)
                        ),
                        ParameterData(
                            "Tempo Total",
                            "h",
                            "",
                            parametersState.germination.totalTime,
                            parametersViewModel.compareGerminationTotalTime(parametersReceived, isBluetoothConnected)
                        ),
                        ParameterData(
                            "Volume de Água",
                            "mL",
                            "",
                            parametersState.germination.waterVolume,
                            parametersViewModel.compareGerminationWaterVolume(parametersReceived, isBluetoothConnected)
                        ),
                        ParameterData(
                            "Adição de Água",
                            "min",
                            "",
                            parametersState.germination.waterAddition,
                            parametersViewModel.compareGerminationWaterAddition(parametersReceived, isBluetoothConnected)
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
                            parametersState.kilning.temperature,
                            parametersViewModel.compareKilningTemperature(parametersReceived, isBluetoothConnected)
                        ),
                        ParameterData(
                            "Tempo",
                            "min",
                            "",
                            parametersState.kilning.time,
                            parametersViewModel.compareKilningTime(parametersReceived, isBluetoothConnected)
                        )
                    )
                )
            )
        )
    )
    return sections
}