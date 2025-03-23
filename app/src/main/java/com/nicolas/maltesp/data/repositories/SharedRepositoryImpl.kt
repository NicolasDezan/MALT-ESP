package com.nicolas.maltesp.data.repositories

import com.nicolas.maltesp.domain.models.Parameters.Companion.initializeParametersState
import com.nicolas.maltesp.domain.models.ParametersState
import com.nicolas.maltesp.domain.repositories.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor() : SharedRepository {
    private val _parameters = MutableStateFlow(initializeParametersState())

    override fun getParameters(): StateFlow<ParametersState> = _parameters.asStateFlow()

    override fun updateParameters(newState: ParametersState) {
        _parameters.value = newState
    }
}