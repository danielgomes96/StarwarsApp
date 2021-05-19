package com.daniel.domain.usecase

import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import kotlinx.coroutines.flow.Flow

interface GetSpeciesDetails {
    suspend fun execute(speciesList: List<String>): Flow<ResultWrapper<List<Species>>>
}
