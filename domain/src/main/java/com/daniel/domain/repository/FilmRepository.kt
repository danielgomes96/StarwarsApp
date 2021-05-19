package com.daniel.domain.repository

import com.daniel.domain.entity.Film
import com.daniel.domain.entity.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    suspend fun getFilmsList(urlsList: List<String>): Flow<ResultWrapper<List<Film>>>
}
