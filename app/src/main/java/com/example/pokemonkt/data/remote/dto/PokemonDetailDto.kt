package com.example.pokemonkt.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("sprites") val sprites: PokemonSpritesDto
)

data class PokemonSpritesDto(
    @SerializedName("front_default") val frontDefault: String?,
    @SerializedName("other") val other: PokemonOtherSpritesDto?
)

data class PokemonOtherSpritesDto(
    @SerializedName("showdown") val showdown: PokemonShowdownSpritesDto?
)

data class PokemonShowdownSpritesDto(
    @SerializedName("front_default") val frontDefault: String?
)

