package com.daniel.domain.repository

import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import kotlinx.coroutines.flow.Flow

interface SpeciesRepository {
    suspend fun getSpeciesDetails(urlsList: List<String>): Flow<ResultWrapper<List<Species>>>
}
