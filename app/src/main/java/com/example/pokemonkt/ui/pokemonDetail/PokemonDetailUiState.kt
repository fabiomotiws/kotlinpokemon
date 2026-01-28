package com.example.pokemonkt.ui.pokemonDetail

import com.example.pokemonkt.domain.model.PokemonDetail

data class PokemonDetailUiState(
    val isLoading: Boolean = false,
    val data: PokemonDetail? = null,
    val errorMessage: String? = null
)
