package com.example.pokemonkt.data.repository

import com.example.pokemonkt.data.remote.PokeApiService
import com.example.pokemonkt.data.remote.mapper.toPokemonDetail
import com.example.pokemonkt.data.remote.mapper.toPokemonListItem
import com.example.pokemonkt.domain.model.PokemonDetail
import com.example.pokemonkt.domain.model.PokemonListItem
import com.example.pokemonkt.domain.repository.PokemonRepository

// chama a API -> recebe DTO -> converte para Model
class PokemonRepositoryImpl(
    private val api: PokeApiService,
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonListItem> {
        val response = api.getPokemonList(limit, offset)
        return response.results.map { it.toPokemonListItem() }
    }

    override suspend fun getPokemonDetail(nameOrId: String): PokemonDetail {
        val dto = api.getPokemonDetail(nameOrId)
        return dto.toPokemonDetail()
    }
}
