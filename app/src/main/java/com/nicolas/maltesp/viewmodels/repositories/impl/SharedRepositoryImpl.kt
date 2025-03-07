package com.nicolas.maltesp.viewmodels.repositories.impl

import com.nicolas.maltesp.others.classes.Parameters.Companion.initializeParametersState
import com.nicolas.maltesp.others.dataclasses.ParametersState
import com.nicolas.maltesp.viewmodels.repositories.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor() : SharedRepository {
    private val _parameters = MutableStateFlow(initializeParametersState())

    override fun getParameters(): StateFlow<ParametersState> = _parameters.asStateFlow()

    override suspend fun updateParameters(newState: ParametersState) {
        _parameters.value = newState
    }
}