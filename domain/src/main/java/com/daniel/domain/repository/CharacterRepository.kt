package com.daniel.domain.repository

import com.daniel.domain.entity.Character
import com.daniel.domain.entity.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(name: String): Flow<ResultWrapper<List<Character>>>
}
