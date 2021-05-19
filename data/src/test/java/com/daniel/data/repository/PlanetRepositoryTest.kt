package com.daniel.data.repository

import com.daniel.data.dto.DTOPlanet
import com.daniel.data.service.StarwarsService
import com.daniel.domain.entity.Planet
import com.daniel.domain.entity.ResultWrapper
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

class PlanetRepositoryTest {

    @MockK
    private val starwarsService: StarwarsService = mockk()
    private val planetRepository = spyk(PlanetRepositoryImpl(starwarsService))

    companion object {
        private const val PLANET_URL = "http://swapi.dev/api/planets/28/"
        private val FAKE_DTO_PLANET = DTOPlanet(
            "Tattoine",
            "20000000"
        )
        private val FAKE_PLANET = Planet(
            "Tattoine",
            "20000000"
        )
        val LOADING_RESPONSE = ResultWrapper.Loading
        val SUCCESS_RESPONSE = ResultWrapper.Success(FAKE_PLANET)
        val ERROR_RESPONSE = ResultWrapper.NetworkError
    }

    @Test
    fun `GIVEN a success response WHEN service is called THEN get planet details`() = runBlocking {
        // given
        val mockResponse = Response.success(FAKE_DTO_PLANET)

        coEvery {
            starwarsService.getPlanetDetails(PLANET_URL)
        } returns mockResponse

        // When
        val flowResponse = planetRepository.getPlanetDetails(
            PLANET_URL
        ).toList()

        val loadingResponse = flowResponse.first()
        val successResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        successResponse shouldBe SUCCESS_RESPONSE
    }

    @Test
    fun `GIVEN network error response WHEN service is called THEN throw an error`() = runBlocking {
        // given
        val exceptionResponse = IOException()

        coEvery {
            starwarsService.getPlanetDetails(PLANET_URL)
        } throws exceptionResponse

        // When
        val flowResponse = planetRepository.getPlanetDetails(
            PLANET_URL
        ).toList()

        val loadingResponse = flowResponse.first()
        val errorResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        errorResponse shouldBe ERROR_RESPONSE
    }
}
