package com.daniel.data.mapper

import com.daniel.data.dto.DTOFilm
import com.daniel.domain.entity.Film
import junit.framework.TestCase
import org.junit.Test

class FilmMapperTest {
    private val filmMapper by lazy {
        FilmMapper()
    }
    companion object {
        val FAKE_DTO_FILM = DTOFilm("Return of the Jedi", "Luke Skywalker has returned to\\r\\nhis home planet of Tatooine in\\r\\nan attempt to rescue his\\r\\nfriend Han Solo from the\\r\\nclutches of the vile gangster\\r\\nJabba the Hutt.\\r\\n\\r\\nLittle does Luke know that the\\r\\nGALACTIC EMPIRE has secretly\\r\\nbegun construction on a new\\r\\narmored space station even\\r\\nmore powerful than the first\\r\\ndreaded Death Star.\\r\\n\\r\\nWhen completed, this ultimate\\r\\nweapon will spell certain doom\\r\\nfor the small band of rebels\\r\\nstruggling to restore freedom\\r\\nto the galaxy...")
        val FAKE_FILM = Film("Return of the Jedi", "Luke Skywalker has returned to\\r\\nhis home planet of Tatooine in\\r\\nan attempt to rescue his\\r\\nfriend Han Solo from the\\r\\nclutches of the vile gangster\\r\\nJabba the Hutt.\\r\\n\\r\\nLittle does Luke know that the\\r\\nGALACTIC EMPIRE has secretly\\r\\nbegun construction on a new\\r\\narmored space station even\\r\\nmore powerful than the first\\r\\ndreaded Death Star.\\r\\n\\r\\nWhen completed, this ultimate\\r\\nweapon will spell certain doom\\r\\nfor the small band of rebels\\r\\nstruggling to restore freedom\\r\\nto the galaxy...")
    }

    @Test
    fun `GIVEN a fake response WHEN transforming it to entity THEN get proper film object`() {
        // Given
        // When
        val transformedFilm = filmMapper.transform(FAKE_DTO_FILM)

        // Then
        TestCase.assertEquals(FAKE_FILM.title, transformedFilm.title)
        TestCase.assertEquals(FAKE_FILM.description, transformedFilm.description)
    }

    @Test
    fun `GIVEN a null fake response WHEN transforming it to entity THEN get proper empty object`() {
        // Given
        // When
        val transformedFilm = filmMapper.transform(null as DTOFilm?)

        // Then
        TestCase.assertEquals("", transformedFilm.title)
        TestCase.assertEquals("", transformedFilm.description)
    }
}
