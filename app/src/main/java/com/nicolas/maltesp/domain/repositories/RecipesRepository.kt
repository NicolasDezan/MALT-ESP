package com.nicolas.maltesp.domain.repositories

import com.nicolas.maltesp.data.local.entities.MaltingRecipe
import kotlinx.coroutines.flow.StateFlow

interface RecipesRepository{
    val recipeNames: StateFlow<List<String>>
    suspend fun refreshRecipeNames()
    suspend fun saveRecipe(maltingRecipe: MaltingRecipe)
    suspend fun loadRecipeByName(name: String)
    suspend fun deleteRecipeByName(name: String)
}