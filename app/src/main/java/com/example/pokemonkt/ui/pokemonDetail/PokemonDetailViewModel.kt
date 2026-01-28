package com.example.pokemonkt.ui.pokemonDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonkt.domain.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    var uiState by mutableStateOf(PokemonDetailUiState(isLoading = true))
        private set

    fun load(nameOrId: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            runCatching {
                repository.getPokemonDetail(nameOrId)
            }.onSuccess { detail ->
                uiState = uiState.copy(isLoading = false, data = detail)
            }.onFailure { t ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = t.message ?: "Erro ao carregar detalhes"
                )
            }
        }
    }
}
