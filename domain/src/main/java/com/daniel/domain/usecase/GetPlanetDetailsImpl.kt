package com.daniel.domain.usecase

import com.daniel.domain.entity.Planet
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow

class GetPlanetDetailsImpl(
    private val planetRepository: PlanetRepository
) : GetPlanetDetails {
    override suspend fun execute(name: String): Flow<ResultWrapper<Planet>> {
        return planetRepository.getPlanetDetails(name)
    }
}
