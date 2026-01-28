package com.example.pokemonkt.ui.pokemonList

import com.example.pokemonkt.domain.model.PokemonListItem

data class PokemonListUiState(

    val query: String = "",

    val isLoading: Boolean = false,
    val items: List<PokemonListItem> = emptyList(),
    val errorMessage: String? = null,
)
