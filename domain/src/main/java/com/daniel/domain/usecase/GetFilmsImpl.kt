package com.daniel.domain.usecase

import com.daniel.domain.entity.Film
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class GetFilmsImpl(
    private val filmRepository: FilmRepository
) : GetFilms {
    override suspend fun execute(filmsList: List<String>): Flow<ResultWrapper<List<Film>>> {
        return filmRepository.getFilmsList(filmsList)
    }
}
