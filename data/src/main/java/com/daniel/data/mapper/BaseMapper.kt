package com.daniel.data.mapper

abstract class BaseMapper<IN, OUT> {

    /**
     * Maps a single [entity]
     */
    abstract fun transform(entity: IN): OUT

    /**
     * Maps a list of [entities].
     */
    fun transformList(entities: List<IN>?): List<OUT> {
        return entities?.map(::transform) ?: emptyList()
    }
}
