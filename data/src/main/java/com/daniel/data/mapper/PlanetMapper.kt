package com.daniel.data.mapper

import com.daniel.data.dto.DTOPlanet
import com.daniel.domain.entity.Planet

class PlanetMapper : BaseMapper<DTOPlanet?, Planet>() {
    override fun transform(entity: DTOPlanet?): Planet {
        return entity?.let {
            Planet(it.name, it.population)
        } ?: Planet("", "")
    }
}
