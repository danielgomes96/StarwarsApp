package com.daniel.domain.usecase

import com.daniel.domain.repository.CharacterRepository
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchCharacterTest {

    private val characterRepository: CharacterRepository = spyk()
    private val useCase: SearchCharacter by lazy {
        SearchCharacterImpl(characterRepository)
    }
    private companion object {
        private const val FAKE_CHARACTER_NAME = "yoda"
    }

    @Test
    fun `GIVEN name WHEN use case is executed THEN repository should be called`() = runBlocking {
        // GIVEN
        // FAKE_CHARACTER_NAME

        // WHEN
        useCase.execute(FAKE_CHARACTER_NAME)

        // THEN
        coVerify(exactly = 1) {
            characterRepository.getCharacters(FAKE_CHARACTER_NAME)
        }
    }
}
