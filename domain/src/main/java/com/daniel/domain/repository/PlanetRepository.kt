package com.daniel.domain.repository

import com.daniel.domain.entity.Planet
import com.daniel.domain.entity.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    suspend fun getPlanetDetails(name: String): Flow<ResultWrapper<Planet>>
}
