package com.example.pokemonkt.data.remote.mapper

import com.example.pokemonkt.data.remote.dto.PokemonDetailDto
import com.example.pokemonkt.domain.model.PokemonDetail
import com.example.pokemonkt.utils.constants.OFFICIAL_ARTWORK_BASE


fun PokemonDetailDto.toPokemonDetail(): PokemonDetail {
    val artwork = "$OFFICIAL_ARTWORK_BASE/$id.png"
    val gif = sprites.other?.showdown?.frontDefault?.takeIf { it.isNotBlank() }

    return PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        artworkUrl = artwork,
        showdownGifUrl = gif
    )
}
