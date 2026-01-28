package com.example.pokemonkt.data.local.mapper

import com.example.pokemonkt.data.local.entity.PokemonEntity
import com.example.pokemonkt.domain.model.PokemonListItem

fun PokemonEntity.toDomain() = PokemonListItem(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun PokemonListItem.toEntity() = PokemonEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)