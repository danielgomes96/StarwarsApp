package com.daniel.data.repository

import com.daniel.data.dto.DTOFilm
import com.daniel.data.service.StarwarsService
import com.daniel.domain.entity.Film
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

class FilmRepositoryTest {
    @MockK
    private val starwarsService: StarwarsService = mockk()

    private val filmRepository = spyk(FilmRepositoryImpl(starwarsService))

    companion object {
        private val FAKE_FILMS_LIST = listOf(Film(
            "Return of the Jedi",
            "He is returning...")
        )
        private val FAKE_DTO_FILM = DTOFilm(
            "Return of the Jedi",
            "He is returning..."
        )
        private val FAKE_FILMS_URLS = listOf(
            "http://swapi.dev/api/films/1/"
        )
        private val LOADING_RESPONSE = ResultWrapper.Loading
        private val EMPTY_RESPONSE = ResultWrapper.Empty
        private val SUCCESS_RESPONSE = ResultWrapper.Success(FAKE_FILMS_LIST)
        val ERROR_RESPONSE = ResultWrapper.NetworkError
    }

    @Test
    fun `GIVEN a success response WHEN service is called THEN get films list`() = runBlocking {
        // Given
        val mockResponse = Response.success(FAKE_DTO_FILM)

        coEvery {
            starwarsService.getFilmDetails(FAKE_FILMS_URLS[0])
        } returns mockResponse

        // When
        val flowResponse = filmRepository.getFilmsList(
            FAKE_FILMS_URLS
        ).toList()

        val loadingResponse = flowResponse.first()
        val successResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        successResponse shouldBe SUCCESS_RESPONSE
    }

    @Test
    fun `GIVEN an empty response WHEN service is called THEN get empty films list`() = runBlocking {
        // Given
        val mockResponse = Response.success(FAKE_DTO_FILM)

        coEvery {
            starwarsService.getFilmDetails(FAKE_FILMS_URLS[0])
        } returns mockResponse

        // When
        val flowResponse = filmRepository.getFilmsList(
            emptyList()
        ).toList()

        val loadingResponse = flowResponse.first()
        val emptyResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        emptyResponse shouldBe EMPTY_RESPONSE
    }

    @Test
    fun `GIVEN network error response WHEN service is called THEN throw an error`() = runBlocking {
        // given
        val exceptionResponse = IOException()

        coEvery {
            starwarsService.getFilmDetails(FAKE_FILMS_URLS[0])
        } throws exceptionResponse

        // When
        val flowResponse = filmRepository.getFilmsList(
            FAKE_FILMS_URLS
        ).toList()

        val loadingResponse = flowResponse.first()
        val errorResponse = flowResponse.last()

        // Then
        loadingResponse shouldBe LOADING_RESPONSE
        errorResponse shouldBe ERROR_RESPONSE
    }
}
