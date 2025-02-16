package com.nicolas.maltesp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ScaffoldViewModel : ViewModel() {

    /*################################################################
    ######################## BOTTOMBAR ###############################
    ##################################################################*/

    private val _bottomBarSelectedItem = MutableStateFlow(0)
    val bottomBarSelectedItem = _bottomBarSelectedItem.asStateFlow()

    fun bottomBarSelectItem(index: Int) {
        _bottomBarSelectedItem.value = index
    }

    /*################################################################
    ######################## FAB #####################################
    ##################################################################*/

    private val _isFabExpanded = MutableStateFlow(false)
    val isFabExpanded = _isFabExpanded.asStateFlow()

    // Alterna a expansão do FloatingActionButton
    fun toggleFab() {
        _isFabExpanded.value = !_isFabExpanded.value
    }


}