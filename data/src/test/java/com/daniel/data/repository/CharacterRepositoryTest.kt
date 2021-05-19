package com.daniel.data.repository

import com.daniel.data.dto.DTOCharacter
import com.daniel.data.dto.DTOCharacterResponse
import com.daniel.data.service.StarwarsService
import com.daniel.domain.entity.Character
import com.daniel.domain.entity.ResultWrapper
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.toList
import java.io.IOException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response

class CharacterRepositoryTest {

    @MockK
    private val starwarsService: StarwarsService = mockk()

    private val characterRepository = spyk(CharacterRepositoryImpl(starwarsService))

    companion object {
        const val FAKE_SEARCHED_CHARACTER = "yoda"
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

        val LOADING_RESPONSE = ResultWrapper.Loading
        val EMPTY_RESPONSE = ResultWrapper.Empty
        val SUCCESS_RESPONSE = ResultWrapper.Success(FAKE_LIST_CHARACTER)
        val ERROR_RESPONSE = ResultWrapper.NetworkError
        val FAKE_DTO_CHARACTER = DTOCharacterResponse(FAKE_DTO_LIST_CHARACTER)
        val FAKE_DTO_EMPTY_CHARACTERS = DTOCharacterResponse(listOf())
    }

    @Test
    fun `GIVEN a success response WHEN service is called THEN get character list`() = runBlocking {
        // given
        val mockResponse = Response.success(FAKE_DTO_CHARACTER)

        coEvery {
            starwarsService.getCharacters(FAKE_SEARCHED_CHARACTER)
        } returns mockResponse

        // When
        val flowResponse = characterRepository.getCharacters(
            FAKE_SEARCHED_CHARACTER
        ).toList()

        val loadingResponse = flowResponse.first()
        val successResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        successResponse shouldBe SUCCESS_RESPONSE
    }

    @Test
    fun `GIVEN an empty response WHEN service is called THEN get empty character list`() = runBlocking {
        // Given
        val mockResponse = Response.success(FAKE_DTO_EMPTY_CHARACTERS)

        coEvery {
            starwarsService.getCharacters(FAKE_SEARCHED_CHARACTER)
        } returns mockResponse

        // When
        val flowResponse = characterRepository.getCharacters(
            FAKE_SEARCHED_CHARACTER
        ).toList()

        val loadingResponse = flowResponse.first()
        val emptyResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        emptyResponse shouldBe EMPTY_RESPONSE
    }

    @Test
    fun `GIVEN a network error response response WHEN getCharacters is called THEN throw an error`() = runBlocking {
        // Given
        val exceptionResponse = IOException()

        coEvery {
            starwarsService.getCharacters(FAKE_SEARCHED_CHARACTER)
        } throws exceptionResponse

        // When
        val flowResponse = characterRepository.getCharacters(
            FAKE_SEARCHED_CHARACTER
        ).toList()

        val loadingResponse = flowResponse.first()
        val errorResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        errorResponse shouldBe ERROR_RESPONSE
    }
}
