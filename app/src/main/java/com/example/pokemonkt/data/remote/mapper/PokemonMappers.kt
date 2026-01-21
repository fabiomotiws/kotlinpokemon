package com.example.pokemonkt.data.remote.mapper

import com.example.pokemonkt.data.remote.dto.NamedApiResourceDto
import com.example.pokemonkt.domain.model.PokemonListItem

// os metodos abaixo existem porque o ViewModel e a View nao sabem como montar a URL da imagem do pokemon
// isso é responsabilidade do Model
private const val OFFICIAL_ARTWORK_BASE =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork"

fun NamedApiResourceDto.toPokemonListItem(): PokemonListItem {
    val id = extractPokemonId(url)

    return PokemonListItem(
        id = id,
        name = name.capitalizeFirst(),
        imageUrl = "$OFFICIAL_ARTWORK_BASE/$id.png",
    )
}

private fun extractPokemonId(url: String): Int {
    val trimmed = url.trimEnd('/')
    val idStr = trimmed.substringAfterLast('/')

    return idStr.toInt()
}

fun String.capitalizeFirst(): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

