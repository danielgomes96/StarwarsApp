package com.daniel.data.mapper

import com.daniel.data.dto.DTOCharacter
import com.daniel.data.dto.DTOCharacterResponse
import com.daniel.domain.entity.Character
import junit.framework.TestCase
import org.junit.Test

class CharacterMapperTest {
    private val characterMapper by lazy {
        CharacterMapper()
    }
    companion object {
        private val FAKE_LIST_CHARACTER = listOf(
            Character(
                name = "Yoda",
                birthYear = "896BBY",
                height = 66,
                homeWorld = "http://swapi.dev/api/planets/28/",
                speciesList = listOf(),
                filmsList = listOf()
            )
        )

        private val FAKE_DTO_LIST_CHARACTER = listOf(
            DTOCharacter(
                name = "Yoda",
                birthYear = "896BBY",
                height = 66,
                homeWorld = "http://swapi.dev/api/planets/28/",
                speciesList = listOf(),
                filmsList = listOf()
            )
        )
        val FAKE_DTO_CHARACTER = DTOCharacterResponse(FAKE_DTO_LIST_CHARACTER)
        val FAKE_EMPTY_CHARACTER_LIST = listOf<Character>()
    }

    @Test
    fun `GIVEN a fake response WHEN transforming it to entity THEN get proper object`() {
        // Given
        // FAKE_DTO_LIST_CHARACTER

        // When
        val transformedCharacterList = characterMapper.transform(FAKE_DTO_CHARACTER)

        // Then
        TestCase.assertEquals(FAKE_LIST_CHARACTER[0].name, transformedCharacterList[0].name)
        TestCase.assertEquals(FAKE_LIST_CHARACTER[0].birthYear, transformedCharacterList[0].birthYear)
        TestCase.assertEquals(FAKE_LIST_CHARACTER[0].height, transformedCharacterList[0].height)
        TestCase.assertEquals(FAKE_LIST_CHARACTER[0].homeWorld, transformedCharacterList[0].homeWorld)
        TestCase.assertEquals(FAKE_LIST_CHARACTER[0].speciesList, transformedCharacterList[0].speciesList)
        TestCase.assertEquals(FAKE_LIST_CHARACTER[0].filmsList, transformedCharacterList[0].filmsList)
    }

    @Test
    fun `GIVEN a fake null response WHEN transforming it to entity THEN get empty object`() {
        // Given
        // FAKE_DTO_LIST_CHARACTER

        // When
        val transformedCharacterList = characterMapper.transform(null as DTOCharacterResponse?)

        // Then
        TestCase.assertEquals(FAKE_EMPTY_CHARACTER_LIST, transformedCharacterList)
    }
}
