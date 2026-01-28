package com.example.pokemonkt.data.remote

import com.example.pokemonkt.data.remote.dto.PokemonDetailDto
import com.example.pokemonkt.data.remote.dto.PokemonListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PokemonListDTO

    @GET("pokemon/{nameOrId}")
    suspend fun getPokemonDetail(
        @Path("nameOrId") nameOrId: String
    ): PokemonDetailDto
}
