package com.daniel.data.mapper

import com.daniel.data.dto.DTOPlanet
import com.daniel.domain.entity.Planet
import junit.framework.TestCase
import org.junit.Test

class PlanetMapperTest {
    private val planetMapper by lazy {
        PlanetMapper()
    }
    companion object {
        val FAKE_DTO_PLANET = DTOPlanet("Naboo", "4500000000")
        val FAKE_PLANET = Planet("Naboo", "4500000000")
    }

    @Test
    fun `GIVEN a fake response WHEN transforming it to entity THEN get proper planet object`() {
        // Given
        // When
        val transformedPlanet = planetMapper.transform(FAKE_DTO_PLANET)

        // Then
        TestCase.assertEquals(FAKE_PLANET.name, transformedPlanet.name)
        TestCase.assertEquals(FAKE_PLANET.population, transformedPlanet.population)
    }

    @Test
    fun `GIVEN a null response WHEN transforming it to entity THEN get an empty object`() {
        // GIVEN
        // WHEN
        val transformedPlanet = planetMapper.transform(null as DTOPlanet?)

        // THEN
        TestCase.assertEquals("", transformedPlanet.name)
        TestCase.assertEquals("", transformedPlanet.population)
    }
}
