package com.daniel.data.mapper

import com.daniel.data.dto.DTOCharacterResponse
import com.daniel.domain.entity.Character

class CharacterMapper : BaseMapper<DTOCharacterResponse?, List<Character>>() {
    override fun transform(entity: DTOCharacterResponse?): List<Character> {
        return entity?.let {
            it.characterList.map {
                Character(
                    name = it?.name.orEmpty(),
                    birthYear = it?.birthYear.orEmpty(),
                    height = it?.height ?: 0,
                    homeWorld = it?.homeWorld.orEmpty(),
                    speciesList = it?.speciesList ?: listOf(),
                    filmsList = it?.filmsList ?: listOf()
                )
            }
        } ?: listOf()
    }
}
