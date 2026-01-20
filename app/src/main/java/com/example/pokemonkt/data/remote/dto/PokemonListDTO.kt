package com.example.pokemonkt.data.remote.dto

import com.google.gson.annotations.SerializedName

// Molde para transformar o JSON da requisição em objetos kotlin
data class PokemonListDTO(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<NamedApiResourceDto>,
)

// Tipo de modelo criado na PokeAPI
data class NamedApiResourceDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)
