package com.nicolas.maltesp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    // Função para carregar os dados de um recipe pelo UID
//    fun loadRecipeByUid(uid: Int) {
//        viewModelScope.launch {
//            val recipe = dao.getRecipeById(uid) // Busca a receita no banco
//            updateParametersStateFromRecipe(recipe) // Atualiza o estado
//        }
//    }

    fun loadRecipeByName(name: String) {
        viewModelScope.launch {
            val uid = dao.getUidByName(name)?: -1
            if (uid != -1){
                val recipe = dao.getRecipeById(uid) // Busca a receita no banco
                updateParametersStateFromRecipe(recipe) // Atualiza o estado
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

class ParametersViewModelFactory(
    private val dao: MaltingRecipeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParametersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ParametersViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
