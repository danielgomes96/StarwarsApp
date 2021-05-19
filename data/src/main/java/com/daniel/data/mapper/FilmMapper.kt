package com.daniel.data.mapper

import com.daniel.data.dto.DTOFilm
import com.daniel.domain.entity.Film

class FilmMapper : BaseMapper<DTOFilm?, Film>() {
    override fun transform(entity: DTOFilm?): Film {
        return entity?.let {
            Film(it.title, it.description)
        } ?: Film("", "")
    }
}
