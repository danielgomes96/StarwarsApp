package com.daniel.data.mapper

import com.daniel.data.dto.DTOSpecies
import com.daniel.domain.entity.Species
import junit.framework.TestCase
import org.junit.Test

class SpeciesMapperTest {

    private val speciesMapper by lazy {
        SpeciesMapper()
    }
    companion object {
        private val FAKE_DTO_SPECIES = DTOSpecies("Droid", "n/a", "http://swapi.dev/api/planets/8/")
        private val FAKE_SPECIES = Species("Droid", "n/a", "http://swapi.dev/api/planets/8/")
    }

    @Test
    fun `GIVEN a fake response WHEN transforming it to entity THEN get proper object`() {
        // GIVEN
        // FAKE_DTO_SPECIES

        // WHEN
        val transformedSpecies = speciesMapper.transform(FAKE_DTO_SPECIES)

        // THEN
        TestCase.assertEquals(FAKE_SPECIES.name, transformedSpecies.name)
        TestCase.assertEquals(FAKE_SPECIES.homeWorld, transformedSpecies.homeWorld)
        TestCase.assertEquals(FAKE_SPECIES.language, transformedSpecies.language)
    }

    @Test
    fun `GIVEN a null fake response WHEN transforming it to entity THEN get an empty object`() {
        // GIVEN
        // null as DTOSpecies?

        // WHEN
        val transformedSpecies = speciesMapper.transform(null as DTOSpecies?)

        // THEN
        TestCase.assertEquals("", transformedSpecies.language)
        TestCase.assertEquals("", transformedSpecies.homeWorld)
        TestCase.assertEquals("", transformedSpecies.name)
    }
}
