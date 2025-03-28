package com.nicolas.maltesp.domain.repositories

import kotlinx.coroutines.flow.StateFlow

interface ScaffoldRepository {
    // BOTTOM BAR
    val bottomBarSelectedItem: StateFlow<Int>
    fun bottomBarSelectItem(index: Int)

    // FAB
    val isFabExpanded: StateFlow<Boolean>
    fun toggleFab()
}