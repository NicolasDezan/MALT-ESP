package com.nicolas.maltesp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.data.MaltingRecipe
import com.nicolas.maltesp.data.MaltingRecipeDao
import com.nicolas.maltesp.data.dataclasses.GerminationState
import com.nicolas.maltesp.data.dataclasses.KilningState
import com.nicolas.maltesp.data.dataclasses.ParametersState
import com.nicolas.maltesp.data.dataclasses.SteepingState
import com.nicolas.maltesp.data.dataclasses.initializeParametersState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ParametersViewModel(private val dao: MaltingRecipeDao) : ViewModel() {
    private val _parametersState = MutableStateFlow(initializeParametersState())
    val parametersState = _parametersState.asStateFlow()

    private val _recipeNames = MutableStateFlow<List<String>>(emptyList())
    val recipeNames = _recipeNames.asStateFlow()

    fun refreshRecipeNames() {
        viewModelScope.launch {
            _recipeNames.value = dao.getAllRecipeNames() // Busca os nomes das receitas
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

    fun updateParametersStateFromParametersState(parametersReceived: ParametersState) {
        _parametersState.value = parametersReceived
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
}


