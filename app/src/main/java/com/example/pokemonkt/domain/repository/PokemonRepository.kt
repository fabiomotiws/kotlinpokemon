package com.example.pokemonkt.domain.repository

import com.example.pokemonkt.domain.model.PokemonDetail
import com.example.pokemonkt.domain.model.PokemonListItem

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonListItem>
    suspend fun getPokemonDetail(nameOrId: String): PokemonDetail
}
