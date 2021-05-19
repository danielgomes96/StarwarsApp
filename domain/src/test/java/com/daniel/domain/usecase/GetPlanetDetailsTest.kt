package com.daniel.domain.usecase

import com.daniel.domain.repository.PlanetRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPlanetDetailsTest {
    private val planetRepository: PlanetRepository = spyk()
    private val useCase: GetPlanetDetails by lazy {
        GetPlanetDetailsImpl(planetRepository)
    }
    private companion object {
        private const val FAKE_PLANET_URL = "http://swapi.dev/api/planets/8/"
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `GIVEN planet url WHEN use case is executed THEN repository should be called`() = runBlocking {
        // GIVEN
        // FAKE_PLANET_URL

        // WHEN
        useCase.execute(FAKE_PLANET_URL)

        // THEN
        coVerify(exactly = 1) {
            planetRepository.getPlanetDetails(FAKE_PLANET_URL)
        }
    }
}
