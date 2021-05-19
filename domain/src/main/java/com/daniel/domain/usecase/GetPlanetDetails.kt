package com.daniel.domain.usecase

import com.daniel.domain.entity.Planet
import com.daniel.domain.entity.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetPlanetDetails {
    suspend fun execute(name: String): Flow<ResultWrapper<Planet>>
}
