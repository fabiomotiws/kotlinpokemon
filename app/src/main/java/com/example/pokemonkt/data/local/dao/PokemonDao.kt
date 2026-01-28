package com.example.pokemonkt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonkt.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<PokemonEntity>)

    // listagem inicial: só 20
    @Query("SELECT * FROM pokemon ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPage(limit: Int, offset: Int): List<PokemonEntity>

    // busca global (já pega id 1000 etc.)
    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :query || '%' ORDER BY id ASC")
    suspend fun searchByName(query: String): List<PokemonEntity>
}