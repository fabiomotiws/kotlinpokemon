package com.example.pokemonkt.data.remote.mapper

import com.example.pokemonkt.data.remote.dto.PokemonDetailDto
import com.example.pokemonkt.domain.model.PokemonDetail

fun PokemonDetailDto.toPokemonDetail(): PokemonDetail {
    return PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        imageUrl = sprites.frontDefault
    )
}
