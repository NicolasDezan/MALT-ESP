package com.nicolas.maltesp.viewmodels.repositories.impl

import com.nicolas.maltesp.viewmodels.repositories.ScaffoldRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ScaffoldRepositoryImpl @Inject constructor() : ScaffoldRepository {

    /*################################################################
    ######################## BOTTOMBAR ###############################
    ##################################################################*/

    private val _bottomBarSelectedItem = MutableStateFlow(0)
    override val bottomBarSelectedItem = _bottomBarSelectedItem.asStateFlow()

    override fun bottomBarSelectItem(index: Int) {
        _bottomBarSelectedItem.value = index
    }


    /*################################################################
    ######################## FAB #####################################
    ##################################################################*/

    private val _isFabExpanded = MutableStateFlow(false)
    override val isFabExpanded = _isFabExpanded.asStateFlow()

    override fun toggleFab() {
        _isFabExpanded.value = !_isFabExpanded.value
    }
}