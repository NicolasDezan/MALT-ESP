package com.nicolas.maltesp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.maltesp.data.local.entities.MaltingRecipe
import com.nicolas.maltesp.domain.repositories.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
) : ViewModel() {
    val recipeNames = recipesRepository.recipeNames

    fun refreshRecipeNames() {
        viewModelScope.launch {
            recipesRepository.refreshRecipeNames()
        }
    }

    fun saveRecipe(maltingRecipe: MaltingRecipe) {
        viewModelScope.launch {
            recipesRepository.saveRecipe(maltingRecipe)
        }
    }

    fun loadRecipeByName(name: String) {
        viewModelScope.launch {
            recipesRepository.loadRecipeByName(name)
        }
    }

    // Função para deletar uma receita pelo nome -> Usada na UI
    fun deleteRecipeByName(name: String) {
        viewModelScope.launch {
            recipesRepository.deleteRecipeByName(name)
        }
    }
}