package com.daniel.domain.usecase

import com.daniel.domain.entity.Character
import com.daniel.domain.entity.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface SearchCharacter {
    suspend fun execute(name: String): Flow<ResultWrapper<List<Character>>>
}
