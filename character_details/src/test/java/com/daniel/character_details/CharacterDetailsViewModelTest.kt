package com.daniel.character_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.daniel.domain.entity.Film
import com.daniel.domain.entity.Planet
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import com.daniel.domain.usecase.GetFilms
import com.daniel.domain.usecase.GetPlanetDetails
import com.daniel.domain.usecase.GetSpeciesDetails
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getPlanetDetails: GetPlanetDetails

    @MockK
    private lateinit var getFilms: GetFilms

    @MockK
    private lateinit var getSpeciesDetails: GetSpeciesDetails

    private lateinit var viewModel: CharacterDetailsViewModel

    companion object {
        private val PLANET = Planet(
            "Tattooine",
            "2000000"
        )
        private const val HOME_WORLD = "http://swapi.dev/api/planets/28/"
        private val EMPTY_SPECIES_URL_LIST = emptyList<String>()
        private val SPECIES_URL_LIST = listOf(
            "http://swapi.dev/api/species/2/"
        )
        private val SPECIES_LIST = listOf(
            Species(
                name = "Yoda",
                language = "896BBY",
                homeWorld = ""
            )
        )
        private val FILM_URL_LIST = listOf(
            "http://swapi.dev/api/films/1/",
            "http://swapi.dev/api/films/2/"
        )
        private val FILMS_LIST = listOf(
            Film(
                title = "The return of the Freezer",
                description = "Food will be rotten in some days..."
            )
        )
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = CharacterDetailsViewModel(
                getPlanetDetails,
                getFilms,
                getSpeciesDetails
        )
    }

    @Test
    fun `GIVEN homeWorld WHEN getPlanetDetails is called THEN getPlanetDetails should be executed`() {
        val flow = runBlocking {
            flow {
                emit(ResultWrapper.Success(PLANET))
            }
        }

        coEvery {
            getPlanetDetails.execute(HOME_WORLD)
        } returns flow

        viewModel.getPlanetDetails(HOME_WORLD)

        coVerify(exactly = 1) { getPlanetDetails.execute(HOME_WORLD) }
        viewModel.planetLiveData.value shouldBe ResultWrapper.Success(PLANET)
    }

    @Test
    fun `GIVEN filmsList WHEN getFilms is called THEN getFilms should be executed`() {
        val flow = runBlocking {
            flow {
                emit(ResultWrapper.Success(FILMS_LIST))
            }
        }

        coEvery {
            getFilms.execute(FILM_URL_LIST)
        } returns flow

        viewModel.getFilms(FILM_URL_LIST)

        coVerify(exactly = 1) { getFilms.execute(FILM_URL_LIST) }
        viewModel.filmsLiveData.value shouldBe ResultWrapper.Success(FILMS_LIST)
    }

    @Test
    fun `GIVEN speciesList WHEN getSpeciesDetails is called THEN getSpeciesDetails should be executed`() {
        val flow = runBlocking {
            flow {
                emit(ResultWrapper.Success(SPECIES_LIST))
            }
        }

        coEvery {
            getSpeciesDetails.execute(SPECIES_URL_LIST)
        } returns flow

        viewModel.getSpeciesDetails(SPECIES_URL_LIST)

        coVerify(exactly = 1) { getSpeciesDetails.execute(SPECIES_URL_LIST) }
        viewModel.speciesLiveData.value shouldBe ResultWrapper.Success(SPECIES_LIST)
    }

    @Test
    fun `GIVEN an empty speciesList WHEN getSpeciesDetails is called THEN getSpeciesDetails should not be executed`() {
        viewModel.getSpeciesDetails(EMPTY_SPECIES_URL_LIST)

        coVerify(exactly = 0) { getSpeciesDetails.execute(SPECIES_URL_LIST) }
        viewModel.speciesLiveData.value shouldBe ResultWrapper.Empty
    }
}
