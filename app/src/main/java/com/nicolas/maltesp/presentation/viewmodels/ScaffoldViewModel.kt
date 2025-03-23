package com.nicolas.maltesp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.nicolas.maltesp.domain.repositories.ScaffoldRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScaffoldViewModel
@Inject constructor(
    private val repository: ScaffoldRepository,
) : ViewModel() {
    val bottomBarSelectedItem = repository.bottomBarSelectedItem

    val isFabExpanded = repository.isFabExpanded

    fun bottomBarSelectItem(index: Int) {
        repository.bottomBarSelectItem(index)
    }

    fun toggleFab() {
        repository.toggleFab()
    }
}