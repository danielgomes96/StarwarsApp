package com.daniel.data.repository

import com.daniel.data.mapper.CharacterMapper
import com.daniel.data.service.StarwarsService
import com.daniel.domain.entity.Character
import com.daniel.domain.entity.ResultWrapper
import com.daniel.domain.repository.CharacterRepository
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CharacterRepositoryImpl(
    private val starwarsService: StarwarsService
) : CharacterRepository {

    override suspend fun getCharacters(name: String): Flow<ResultWrapper<List<Character>>> = flow {
        emit(ResultWrapper.Loading)
        runCatching {
            starwarsService.getCharacters(name)
        }.onSuccess {
            if (it.body()?.characterList.isNullOrEmpty()) {
                emit(ResultWrapper.Empty)
            } else {
                emit(ResultWrapper.Success(CharacterMapper().transform(it.body())))
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
