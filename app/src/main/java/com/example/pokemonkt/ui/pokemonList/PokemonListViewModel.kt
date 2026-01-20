package com.example.pokemonkt.ui.pokemonList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonkt.domain.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repository: PokemonRepository,
) : ViewModel() {
    var uiState by mutableStateOf(PokemonListUiState(isLoading = true))
        private set

    init {
        loadPokemon()
    }

    fun loadPokemon(
        limit: Int = 20,
        offset: Int = 0,
    ) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            runCatching {
                repository.getPokemonList(limit, offset)
            }.onSuccess { items ->
                uiState =
                    uiState.copy(
                        isLoading = false,
                        items = items,
                    )
            }.onFailure { throwable ->
                uiState =
                    uiState.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Erro ao carregar os pokemons",
                    )
            }
        }
    }
}
