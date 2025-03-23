package com.nicolas.maltesp.domain.repositories

import com.nicolas.maltesp.domain.models.ParametersState
import kotlinx.coroutines.flow.StateFlow

interface SharedRepository {
    fun getParameters(): StateFlow<ParametersState>
    fun updateParameters(newState: ParametersState)
}