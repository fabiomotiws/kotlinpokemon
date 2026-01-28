package com.example.pokemonkt.data.repository

import com.example.pokemonkt.data.local.dao.PokemonDao
import com.example.pokemonkt.data.local.mapper.toDomain
import com.example.pokemonkt.data.local.mapper.toEntity
import com.example.pokemonkt.data.remote.PokeApiService
import com.example.pokemonkt.data.remote.mapper.toPokemonDetail
import com.example.pokemonkt.data.remote.mapper.toPokemonListItem
import com.example.pokemonkt.domain.model.PokemonDetail
import com.example.pokemonkt.domain.model.PokemonListItem
import com.example.pokemonkt.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepositoryImpl(
    private val api: PokeApiService, private val dao: PokemonDao
) : PokemonRepository {

    private var cachedList: List<PokemonListItem>? = null
    private val cachedDetails = mutableMapOf<String, PokemonDetail>()

    override suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonListItem> {
        // cache apenas para a “lista atual”
        val current = cachedList
        if (current != null && limit == 20 && offset == 0) return current

        val response = api.getPokemonList(limit, offset)
        val items = response.results.map { it.toPokemonListItem() }

        // só cacheia a primeira página por enquanto (seu requisito)
        if (limit == 20 && offset == 0) cachedList = items

        return items
    }

    override suspend fun getPokemonDetail(nameOrId: String): PokemonDetail {
        val key = nameOrId.lowercase()
        cachedDetails[key]?.let { return it }

        val dto = api.getPokemonDetail(nameOrId)
        val detail = dto.toPokemonDetail()

        cachedDetails[key] = detail
        return detail
    }

    override fun clearSessionCache() {
        cachedList = null
        cachedDetails.clear()
    }


    override suspend fun ensurePokemonCache() = withContext(Dispatchers.IO) {
        if (dao.count() > 0) return@withContext

        // 1) pega count
        val count = api.getPokemonList(limit = 1, offset = 0).count

        // 2) busca todos
        val all = api.getPokemonList(limit = count, offset = 0)
            .results
            .map { it.toPokemonListItem() } // seu mapper que extrai id pela url e monta imageUrl

        // 3) salva
        dao.upsertAll(all.map { it.toEntity() })
    }

    override suspend fun getPokemonListPage(limit: Int, offset: Int): List<PokemonListItem> =
        withContext(Dispatchers.IO) {
            dao.getPage(limit, offset).map { it.toDomain() }
        }

    override suspend fun searchPokemonByName(query: String): List<PokemonListItem> =
        withContext(Dispatchers.IO) {
            dao.searchByName(query.trim().lowercase()).map { it.toDomain() }
        }
}
