package com.example.pokemonkt.ui.pokemonList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonkt.domain.model.PokemonListItem
import com.example.pokemonkt.domain.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repository: PokemonRepository,
) : ViewModel() {
    var uiState by mutableStateOf(PokemonListUiState(isLoading = true))
        private set

    init {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            repository.ensurePokemonCache()
            val page = repository.getPokemonListPage(20, 0)
            uiState = uiState.copy(isLoading = false, items = page)
        }
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

    fun onQueryChange(newQuery: String) {
        uiState = uiState.copy(query = newQuery)
        viewModelScope.launch {
            uiState = if (newQuery.isBlank()) {
                uiState.copy(items = repository.getPokemonListPage(20, 0))
            } else {
                uiState.copy(items = repository.searchPokemonByName(newQuery))
            }
        }
    }

    fun clearQuery() {
        uiState = uiState.copy(query = "")
    }

    val visibleItems: List<PokemonListItem>
        get() {
            val query = uiState.query.trim().lowercase()
            if(query.isEmpty()) return uiState.items
            return uiState.items.filter { it.name.lowercase().contains(query) }
        }
}
