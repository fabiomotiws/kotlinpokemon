package com.example.pokemonkt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemonkt.data.local.dao.PokemonDao
import com.example.pokemonkt.data.local.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
