package com.example.pokemonkt.data.remote.mapper

import com.example.pokemonkt.data.remote.dto.PokemonDetailDto
import com.example.pokemonkt.domain.model.PokemonDetail
import com.example.pokemonkt.utils.constants.OFFICIAL_ARTWORK_BASE


fun PokemonDetailDto.toPokemonDetail(): PokemonDetail {
    val showdownGif = sprites.other?.showdown?.frontDefault
    val normalFront = sprites.frontDefault
    val officialArtwork = "$OFFICIAL_ARTWORK_BASE/$id.png"

    android.util.Log.d("PokemonDetailMapper", "showdownGif=$showdownGif")
    val a = showdownGif == null
    android.util.Log.d("PokemonDetailMapper", "{$a}")

    val chosenImageUrl = showdownGif ?: normalFront ?: officialArtwork
    android.util.Log.d("PokemonDetailMapper", "chosenImageUrl=$chosenImageUrl")

    return PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        imageUrl = chosenImageUrl
    )
}
