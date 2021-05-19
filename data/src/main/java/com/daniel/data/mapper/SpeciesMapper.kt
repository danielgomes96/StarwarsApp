package com.daniel.data.mapper

import com.daniel.data.dto.DTOSpecies
import com.daniel.domain.entity.Species

class SpeciesMapper : BaseMapper<DTOSpecies?, Species>() {
    override fun transform(entity: DTOSpecies?): Species {
        return entity?.let {
            Species(it.name.orEmpty(), it.language.orEmpty(), it.homeWorld.orEmpty())
        } ?: Species("", "", "")
    }
}
