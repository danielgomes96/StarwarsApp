package com.daniel.domain.usecase

import com.daniel.domain.repository.FilmRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetFilmsTest {
    private val filmRepository: FilmRepository = spyk()
    private val useCase: GetFilms by lazy {
        GetFilmsImpl(filmRepository)
    }
    private companion object {
        private val FAKE_FILMS_URLS = listOf(
            "http://swapi.dev/api/films/1/",
            "http://swapi.dev/api/films/2/"
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `GIVEN films urls WHEN use case is executed THEN repository should be called`() = runBlocking {
        // GIVEN
        // FAKE_FILMS_URLS

        // WHEN
        useCase.execute(FAKE_FILMS_URLS)

        // THEN
        coVerify(exactly = 1) {
            filmRepository.getFilmsList(FAKE_FILMS_URLS)
        }
    }
}
