package com.daniel.character_search

import com.daniel.domain.entity.Character
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.usecase.SearchCharacterImpl
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterSearchViewModelTest {

    private val useCase: SearchCharacterImpl = mockk()
    private val viewModel = spyk(
        CharacterSearchViewModel(useCase)
    )
    companion object {
        private const val SEARCHED_NAME = "yoda"
    }

    @Test
    fun `GIVEN name WHEN getCharacters is called THEN searchCharacter should be executed`() {
        val characterList = listOf<Character>()
        val flow = runBlocking {
            flow {
                emit(ResultWrapper.Success(characterList))
            }
        }
        coEvery {
            useCase.execute(SEARCHED_NAME)
        } returns flow

        viewModel.getCharacters(SEARCHED_NAME)

        verify(exactly = 1) { viewModel.getCharacters(SEARCHED_NAME) }
    }
}
