package com.nicolas.maltesp.viewmodels.repositories

import com.nicolas.maltesp.others.dataclasses.ParametersState
import kotlinx.coroutines.flow.StateFlow

interface SharedRepository {
    fun getParameters(): StateFlow<ParametersState>
    suspend fun updateParameters(newState: ParametersState)
}