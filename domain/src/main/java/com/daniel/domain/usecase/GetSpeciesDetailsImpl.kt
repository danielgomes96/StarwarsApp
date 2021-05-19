package com.daniel.domain.usecase

import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import com.daniel.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.Flow

class GetSpeciesDetailsImpl(
    private val speciesRepository: SpeciesRepository
) : GetSpeciesDetails {
    override suspend fun execute(speciesList: List<String>): Flow<ResultWrapper<List<Species>>> {
        return speciesRepository.getSpeciesDetails(speciesList)
    }
}
