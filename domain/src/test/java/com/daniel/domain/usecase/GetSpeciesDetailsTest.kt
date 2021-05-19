package com.daniel.domain.usecase

import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.entity.Species
import com.daniel.domain.repository.SpeciesRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSpeciesDetailsTest {
    private val speciesRepository: SpeciesRepository = spyk()
    private val useCase: GetSpeciesDetails by lazy {
        GetSpeciesDetailsImpl(speciesRepository)
    }
    private companion object {
        private val FAKE_SPECIES_URLS = listOf(
            "http://swapi.dev/api/species/2/"
        )
        private val FAKE_LIST_CHARACTER = listOf(
            Species(
                name = "Yoda",
                language = "896BBY",
                homeWorld = ""
            )
        )
        val SUCCESS_RESPONSE = ResultWrapper.Success(FAKE_LIST_CHARACTER)
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `GIVEN species urls WHEN use case is executed THEN repository should be called`() = runBlocking {
        // GIVEN
        // FAKE_SPECIES_URLS

        // THEN
        useCase.execute(FAKE_SPECIES_URLS)

        // WHEN
        coVerify(exactly = 1) {
            speciesRepository.getSpeciesDetails(FAKE_SPECIES_URLS)
        }
    }
}
