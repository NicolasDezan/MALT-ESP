package com.nicolas.maltesp.data.repositories

import androidx.compose.runtime.mutableStateOf
import com.nicolas.maltesp.data.local.entities.MaltingRecipe
import com.nicolas.maltesp.data.local.dao.MaltingRecipeDao
import com.nicolas.maltesp.domain.models.GerminationState
import com.nicolas.maltesp.domain.models.KilningState
import com.nicolas.maltesp.domain.models.ParametersState
import com.nicolas.maltesp.domain.models.SteepingState
import com.nicolas.maltesp.domain.repositories.RecipesRepository
import com.nicolas.maltesp.domain.repositories.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val dao: MaltingRecipeDao,
    private val sharedRepository: SharedRepository
) : RecipesRepository {

    private val _recipeNames = MutableStateFlow<List<String>>(emptyList())
    override val recipeNames: StateFlow<List<String>> = _recipeNames.asStateFlow()

    override suspend fun refreshRecipeNames() {
        _recipeNames.value = dao.getAllRecipeNames()
    }

    override suspend fun saveRecipe(maltingRecipe: MaltingRecipe) {
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
        dao.insertAll(recipe)
    }

    override suspend fun loadRecipeByName(name: String) {
        val uid = dao.getUidByName(name) ?: -1
        if (uid != -1) {
            val recipe = dao.getRecipeById(uid)
            val parametersLoaded = ParametersState(
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
            sharedRepository.updateParameters(parametersLoaded)
        }
    }

    override suspend fun deleteRecipeByName(name: String) {
        val uid = dao.getUidByName(name)
        if (uid != null){
            dao.deleteByUid(uid)
        }
    }
}