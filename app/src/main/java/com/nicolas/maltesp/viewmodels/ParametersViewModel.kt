package com.nicolas.maltesp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.data.MaltingRecipe
import com.nicolas.maltesp.data.MaltingRecipeDao
import com.nicolas.maltesp.others.classes.Parameters
import com.nicolas.maltesp.others.dataclasses.GerminationState
import com.nicolas.maltesp.others.dataclasses.KilningState
import com.nicolas.maltesp.others.dataclasses.ParametersState
import com.nicolas.maltesp.others.dataclasses.SteepingState
import com.nicolas.maltesp.others.objects.MinRangeValues
import com.nicolas.maltesp.others.objects.MultiplierRange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ParametersViewModel(private val dao: MaltingRecipeDao) : ViewModel() {

    private val _parametersState = MutableStateFlow(Parameters.initializeParametersState())
    val parametersState = _parametersState.asStateFlow()

    private val _parametersRange = MutableStateFlow(Parameters.initializeRangeGroup())
    val parametersRange = _parametersRange.asStateFlow()


    /*################################################################
    ######################## ROOM-DAO ################################
    ##################################################################*/

    private val _recipeNames = MutableStateFlow<List<String>>(emptyList())
    val recipeNames = _recipeNames.asStateFlow()

    fun refreshRecipeNames() {
        viewModelScope.launch {
            _recipeNames.value = dao.getAllRecipeNames() // Busca os nomes das receitas
        }
    }

    fun saveRecipe(maltingRecipe: MaltingRecipe) {
        viewModelScope.launch {
            // Cria um objeto MaltingRecipe com os valores do estado atual
            val recipe = MaltingRecipe(
                uid = maltingRecipe.uid,
                recipeName = maltingRecipe.recipeName,
                steepingSubmergedTime = maltingRecipe.steepingSubmergedTime,
                steepingWaterVolume = maltingRecipe.steepingWaterVolume,
                steepingRestTime = maltingRecipe.steepingRestTime,
                steepingCycles = maltingRecipe.steepingCycles,
                germinationRotationLevel = maltingRecipe.germinationRotationLevel,
                germinationTotalTime = maltingRecipe.germinationTotalTime,
                germinationWaterVolume = maltingRecipe.germinationWaterVolume,
                germinationWaterAddition = maltingRecipe.germinationWaterAddition,
                kilningTemperature = maltingRecipe.kilningTemperature,
                kilningTime = maltingRecipe.kilningTime
            )
            // Insere a receita no banco
            dao.insertAll(recipe)
        }
    }

    fun loadRecipeByName(name: String) {
        viewModelScope.launch {
            val uid = dao.getUidByName(name)?: -1
            if (uid != -1){
                val recipe = dao.getRecipeById(uid) // Busca a receita no banco
                updateParametersStateFromRecipe(recipe) // Atualiza o estado
            }
        }
    }

    fun deleteRecipeByName(name: String) {
        viewModelScope.launch {
            val uid = dao.getUidByName(name)
            if (uid != null){
                dao.deleteByUid(uid)
            }
        }
    }

    private fun updateParametersStateFromRecipe(recipe: MaltingRecipe) {
        _parametersState.value = ParametersState(
            steeping = SteepingState(
                submergedTime = mutableStateOf(recipe.steepingSubmergedTime ?: ""),
                waterVolume = mutableStateOf(recipe.steepingWaterVolume ?: ""),
                restTime = mutableStateOf(recipe.steepingRestTime ?: ""),
                cycles = mutableStateOf(recipe.steepingCycles ?: "")
            ),
            germination = GerminationState(
                rotationLevel = mutableStateOf(recipe.germinationRotationLevel ?: ""),
                totalTime = mutableStateOf(recipe.germinationTotalTime ?: ""),
                waterVolume = mutableStateOf(recipe.germinationWaterVolume ?: ""),
                waterAddition = mutableStateOf(recipe.germinationWaterAddition ?: "")
            ),
            kilning = KilningState(
                temperature = mutableStateOf(recipe.kilningTemperature ?: ""),
                time = mutableStateOf(recipe.kilningTime ?: "")
            )
        )
    }


    /*################################################################
    ######################## BLUETOOTH ###############################
    ##################################################################*/

    fun updateParametersStateFromParametersReceived(parametersReceived: ParametersState) {
        _parametersState.value = parametersReceived
    }

    fun compareSteepingSubmergedTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.steeping.submergedTime.value.toFloat() == parametersReceived.steeping.submergedTime.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareSteepingWaterVolume(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.steeping.waterVolume.value.toFloat() == parametersReceived.steeping.waterVolume.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareSteepingRestTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.steeping.restTime.value.toFloat() == parametersReceived.steeping.restTime.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareSteepingCycles(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.steeping.cycles.value.toFloat() == parametersReceived.steeping.cycles.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareGerminationTotalTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.germination.totalTime.value.toFloat() == parametersReceived.germination.totalTime.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareGerminationWaterVolume(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.germination.waterVolume.value.toFloat() == parametersReceived.germination.waterVolume.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareGerminationWaterAddition(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.germination.waterAddition.value.toFloat() == parametersReceived.germination.waterAddition.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareGerminationRotationLevel(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.germination.rotationLevel.value.toFloat() == parametersReceived.germination.rotationLevel.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareKilningTemperature(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.kilning.temperature.value.toFloat() == parametersReceived.kilning.temperature.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun compareKilningTime(parametersReceived: ParametersState, isBluetoothConnected: Boolean = false): Boolean? {
        return try{
            if(isBluetoothConnected) {
                _parametersState.value.kilning.time.value.toFloat() == parametersReceived.kilning.time.value.toFloat()
            } else null
        } catch (e: NumberFormatException) {
            false
        }
    }


    /*################################################################
    ################# NUMBER VALIDATION ##############################
    ##################################################################*/

    fun isAllParametersValid(): Boolean {
        return  isSteepingSubmergedTimeValid() &&
                isSteepingWaterVolumeValid() &&
                isSteepingRestTimeValid() &&
                isSteepingCyclesValid() &&
                isGerminationTotalTimeValid() &&
                isGerminationWaterVolumeValid() &&
                isGerminationWaterAdditionValid() &&
                isGerminationRotationLevelValid() &&
                isKilningTemperatureValid() &&
                isKilningTimeValid()
    }

    fun isSteepingSubmergedTimeValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.steeping.submergedTime.min
            val rangeMax = _parametersRange.value.steeping.submergedTime.max
            val submergedTime = _parametersState.value.steeping.submergedTime.value.toFloat()
            submergedTime in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun isSteepingWaterVolumeValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.steeping.waterVolume.min
            val rangeMax = _parametersRange.value.steeping.waterVolume.max
            val waterVolume = _parametersState.value.steeping.waterVolume.value.toFloat()
            waterVolume in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun isSteepingRestTimeValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.steeping.restTime.min
            val rangeMax = _parametersRange.value.steeping.restTime.max
            val restTime = _parametersState.value.steeping.restTime.value.toFloat()
            restTime in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun isSteepingCyclesValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.steeping.cycles.min
            val rangeMax = _parametersRange.value.steeping.cycles.max
            val cycles = _parametersState.value.steeping.cycles.value.toFloat()
            cycles in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun isGerminationTotalTimeValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.germination.totalTime.min
            val rangeMax = _parametersRange.value.germination.totalTime.max
            val totalTime = _parametersState.value.germination.totalTime.value.toFloat()
            totalTime in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun isGerminationWaterVolumeValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.germination.waterVolume.min
            val rangeMax = _parametersRange.value.germination.waterVolume.max
            val waterVolume = _parametersState.value.germination.waterVolume.value.toFloat()
            waterVolume in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
        }

    fun isGerminationWaterAdditionValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.germination.waterAddition.min
            val rangeMax = _parametersRange.value.germination.waterAddition.max
            val waterAddition = _parametersState.value.germination.waterAddition.value.toFloat()
            waterAddition in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun isGerminationRotationLevelValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.germination.rotationLevel.min
            val rangeMax = _parametersRange.value.germination.rotationLevel.max
            val rotationLevel = _parametersState.value.germination.rotationLevel.value.toFloat()
            rotationLevel in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun isKilningTemperatureValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.kilning.temperature.min
            val rangeMax = _parametersRange.value.kilning.temperature.max
            val temperature = _parametersState.value.kilning.temperature.value.toFloat()
            temperature in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun isKilningTimeValid(): Boolean {
        return try{
            val rangeMin = _parametersRange.value.kilning.time.min
            val rangeMax = _parametersRange.value.kilning.time.max
            val time = _parametersState.value.kilning.time.value.toFloat()
            time in rangeMin..rangeMax
        } catch (e: NumberFormatException) { false }
    }

    fun parametersToByteArray(): ByteArray? {
        return try {
            val bytes = byteArrayOf(
                /*...0...*/(-128), // == 0: IDENTIFICADOR DE PARAMETROS -- WRITE

                /*...1...*/parameterToByte(_parametersState.value.steeping.submergedTime.value, MinRangeValues.Steeping.SUBMERGED_TIME, MultiplierRange.Steeping.SUBMERGED_TIME),
                /*...2...*/parameterToByte(_parametersState.value.steeping.waterVolume.value, MinRangeValues.Steeping.WATER_VOLUME, MultiplierRange.Steeping.WATER_VOLUME),
                /*...3...*/parameterToByte(_parametersState.value.steeping.restTime.value, MinRangeValues.Steeping.REST_TIME, MultiplierRange.Steeping.REST_TIME),
                /*...4...*/parameterToByte(_parametersState.value.steeping.cycles.value, MinRangeValues.Steeping.CYCLES, MultiplierRange.Steeping.CYCLES),

                /*...5...*/parameterToByte(_parametersState.value.germination.rotationLevel.value, MinRangeValues.Germination.ROTATION_LEVEL, MultiplierRange.Germination.ROTATION_LEVEL),
                /*...6...*/parameterToByte(_parametersState.value.germination.totalTime.value, MinRangeValues.Germination.TOTAL_TIME, MultiplierRange.Germination.TOTAL_TIME),
                /*...7...*/parameterToByte(_parametersState.value.germination.waterVolume.value, MinRangeValues.Germination.WATER_VOLUME, MultiplierRange.Germination.WATER_VOLUME),
                /*...8...*/parameterToByte(_parametersState.value.germination.waterAddition.value, MinRangeValues.Germination.WATER_ADDITION, MultiplierRange.Germination.WATER_ADDITION),

                /*...9...*/parameterToByte(_parametersState.value.kilning.temperature.value, MinRangeValues.Kilning.TEMPERATURE, MultiplierRange.Kilning.TEMPERATURE),
                /*...10..*/parameterToByte(_parametersState.value.kilning.time.value, MinRangeValues.Kilning.TIME, MultiplierRange.Kilning.TIME)
            )
            bytes // Retorno do array de bytes
        } catch (e: NumberFormatException) {
            null // Retorno nulo em caso de exceção
        }
    }

    // Transformo os valores em um int de 0 a 255 e depois subtraio por 128 para que os valores fiquem entre -128 e 127
    private fun parameterToByte(parameter: String, minParameterValue: Float, multiplierRange: Float): Byte {
        val byteComum = ((parameter.toFloat() - minParameterValue) / multiplierRange).toInt()
        val byteKotlin = ((byteComum - 128).toByte())
        return byteKotlin
    }
}