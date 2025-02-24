package com.nicolas.maltesp.ui.navigation.screens.home.bottombar.content.parameters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nicolas.maltesp.others.dataclasses.ParametersRange
import com.nicolas.maltesp.viewmodels.BluetoothViewModel
import com.nicolas.maltesp.viewmodels.ParametersViewModel

data class ParameterData(
    val name: String,
    val unit: String,
    val description: String,
    val state: MutableState<String>,
    val range: ParametersRange,
    val isEquals: Boolean?, // Se nulo, significa que há problema na conexão
    val isNumberValid: Boolean = false
)

data class ParameterGroup(
    val title: String? = null,
    val parameters: List<ParameterData>
)

data class ParameterSectionData(
    val title: String,
    val groups: List<ParameterGroup>
)

@Composable
fun parameterSectionData(parametersViewModel: ParametersViewModel, bluetoothViewModel: BluetoothViewModel): List<ParameterSectionData> {
    val parametersState by parametersViewModel.parametersState.collectAsState()
    val parametersRange by parametersViewModel.parametersRange.collectAsState()
    val parametersReceived by bluetoothViewModel.parametersReceived.collectAsState()
    val isBluetoothConnected = bluetoothViewModel.isConnected()

    val sections = listOf(
        ParameterSectionData(
            title = "Maceração",
            groups = listOf(
                ParameterGroup(
                    parameters = listOf(
                        ParameterData(
                            name = "Tempo Submerso",
                            unit = "h",
                            description = "",
                            state = parametersState.steeping.submergedTime,
                            range = parametersRange.steeping.submergedTime,
                            isEquals = parametersViewModel.compareSteepingSubmergedTime(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isSteepingSubmergedTimeValid()
                        ),
                        ParameterData(
                            name = "Volume de Água",
                            unit = "mL",
                            description = "",
                            state = parametersState.steeping.waterVolume,
                            range = parametersRange.steeping.waterVolume,
                            isEquals = parametersViewModel.compareSteepingWaterVolume(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isSteepingWaterVolumeValid()
                        ),
                        ParameterData(
                            name = "Tempo Descanso",
                            unit = "h",
                            description = "",
                            state = parametersState.steeping.restTime,
                            range = parametersRange.steeping.restTime,
                            isEquals = parametersViewModel.compareSteepingRestTime(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isSteepingRestTimeValid()
                        ),
                        ParameterData(
                            name = "Número de Ciclos",
                            unit = "",
                            description = "",
                            state = parametersState.steeping.cycles,
                            range = parametersRange.steeping.cycles,
                            isEquals = parametersViewModel.compareSteepingCycles(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isSteepingCyclesValid()
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
                            name = "Nível de Rotação",
                            unit = "",
                            description = "",
                            state = parametersState.germination.rotationLevel,
                            range = parametersRange.germination.rotationLevel,
                            isEquals = parametersViewModel.compareGerminationRotationLevel(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isGerminationRotationLevelValid()
                        ),
                        ParameterData(
                            name = "Tempo Total",
                            unit = "h",
                            description = "",
                            state = parametersState.germination.totalTime,
                            range = parametersRange.germination.totalTime,
                            isEquals = parametersViewModel.compareGerminationTotalTime(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isGerminationTotalTimeValid()
                        ),
                        ParameterData(
                            name = "Volume de Água",
                            unit = "mL",
                            description = "",
                            state = parametersState.germination.waterVolume,
                            range = parametersRange.germination.waterVolume,
                            isEquals = parametersViewModel.compareGerminationWaterVolume(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isGerminationWaterVolumeValid()
                        ),
                        ParameterData(
                            name = "Adição de Água",
                            unit = "min",
                            description = "",
                            state = parametersState.germination.waterAddition,
                            range = parametersRange.germination.waterAddition,
                            isEquals = parametersViewModel.compareGerminationWaterAddition(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isGerminationWaterAdditionValid()
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
                            name = "Temperatura",
                            unit = "°C",
                            description = "",
                            state = parametersState.kilning.temperature,
                            range = parametersRange.kilning.temperature,
                            isEquals = parametersViewModel.compareKilningTemperature(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isKilningTemperatureValid()
                        ),
                        ParameterData(
                            name = "Tempo",
                            unit = "min",
                            description = "",
                            state = parametersState.kilning.time,
                            range = parametersRange.kilning.time,
                            isEquals = parametersViewModel.compareKilningTime(parametersReceived, isBluetoothConnected),
                            isNumberValid = parametersViewModel.isKilningTimeValid()
                        )
                    )
                )
            )
        )
    )
    return sections
}