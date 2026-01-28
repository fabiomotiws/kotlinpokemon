package com.example.pokemonkt.domain.repository

import com.example.pokemonkt.domain.model.PokemonDetail
import com.example.pokemonkt.domain.model.PokemonListItem

interface PokemonRepository {
    suspend fun ensurePokemonCache()
    suspend fun getPokemonListPage(limit: Int, offset: Int): List<PokemonListItem>
    suspend fun searchPokemonByName(query: String): List<PokemonListItem>
    suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonListItem>
    suspend fun getPokemonDetail(nameOrId: String): PokemonDetail

    fun clearSessionCache()
}
