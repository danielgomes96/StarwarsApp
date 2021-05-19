package com.daniel.data.repository

import com.daniel.data.dto.DTOSpecies
import com.daniel.data.service.StarwarsService
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import java.io.IOException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response

class SpeciesRepositoryTest {
    @MockK
    private val starwarsService: StarwarsService = mockk()

    private val speciesRepository = spyk(SpeciesRepositoryImpl(starwarsService))

    companion object {
        val FAKE_SPECIES_LIST_URL = listOf(
            "http://swapi.dev/api/species/2/"
        )
        private val FAKE_SPECIES = listOf(
            Species(
                name = "Yoda",
                language = "896BBY",
                homeWorld = "http://swapi.dev/api/planets/28/"
            )
        )

        private val FAKE_DTO_SPECIES = DTOSpecies(
                name = "Yoda",
                language = "896BBY",
                homeWorld = "http://swapi.dev/api/planets/28/"
        )

        val LOADING_RESPONSE = ResultWrapper.Loading
        val EMPTY_RESPONSE = ResultWrapper.Empty
        val SUCCESS_RESPONSE = ResultWrapper.Success(FAKE_SPECIES)
        val ERROR_RESPONSE = ResultWrapper.NetworkError
    }

    @Test
    fun `GIVEN a success response WHEN service is called THEN get species list`() = runBlocking {
        // Given
        val mockResponse = Response.success(FAKE_DTO_SPECIES)

        coEvery {
            starwarsService.getSpeciesDetails(FAKE_SPECIES_LIST_URL[0])
        } returns mockResponse

        // When
        val flowResponse = speciesRepository.getSpeciesDetails(
            FAKE_SPECIES_LIST_URL
        ).toList()

        val loadingResponse = flowResponse.first()
        val successResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        successResponse shouldBe SUCCESS_RESPONSE
    }

    @Test
    fun `GIVEN an empty response WHEN service is called THEN get empty species list`() = runBlocking {
        // When
        val flowResponse = speciesRepository.getSpeciesDetails(
            listOf()
        ).toList()

        val loadingResponse = flowResponse.first()
        val emptyResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        emptyResponse shouldBe EMPTY_RESPONSE
    }

    @Test
    fun `GIVEN network error response WHEN service is called THEN throw an error`() = runBlocking {
        // Given
        val exceptionResponse = IOException()

        coEvery {
            starwarsService.getSpeciesDetails(FAKE_SPECIES_LIST_URL[0])
        } throws exceptionResponse

        // When
        val flowResponse = speciesRepository.getSpeciesDetails(
            FAKE_SPECIES_LIST_URL
        ).toList()

        val loadingResponse = flowResponse.first()
        val errorResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        errorResponse shouldBe ERROR_RESPONSE
    }
}
