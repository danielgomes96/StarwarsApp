package com.daniel.data.repository

import com.daniel.data.mapper.FilmMapper
import com.daniel.data.service.StarwarsService
import com.daniel.domain.entity.Film
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.repository.FilmRepository
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class FilmRepositoryImpl(
    private val starwarsService: StarwarsService
) : FilmRepository {
    override suspend fun getFilmsList(urlsList: List<String>): Flow<ResultWrapper<List<Film>>> = flow {
        emit(ResultWrapper.Loading)
        val filmList = mutableListOf<Film>()
        runCatching {
            urlsList.map { url ->
                starwarsService.getFilmDetails(url)
            }
        }.onSuccess { responseList ->
            if (responseList.isNotEmpty()) {
                responseList.map { response ->
                    response.body()?.let {
                        filmList.add(FilmMapper().transform(it))
                    }
                }
                emit(ResultWrapper.Success(filmList))
            } else {
                emit(ResultWrapper.Empty)
            }
        }.onFailure { throwable ->
            when (throwable) {
                is IOException -> emit(ResultWrapper.NetworkError)
                is HttpException -> emit(ResultWrapper.GenericError(throwable.code(), throwable.message))
                else -> emit(ResultWrapper.GenericError(null, throwable.message))
            }
        }
    }
}
