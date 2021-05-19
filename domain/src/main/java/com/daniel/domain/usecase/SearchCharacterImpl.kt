package com.daniel.domain.usecase

import com.daniel.domain.entity.Character
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class SearchCharacterImpl constructor(
    private val characterRepository: CharacterRepository
) : SearchCharacter {
    override suspend fun execute(name: String): Flow<ResultWrapper<List<Character>>> {
        return characterRepository.getCharacters(name)
    }
}
