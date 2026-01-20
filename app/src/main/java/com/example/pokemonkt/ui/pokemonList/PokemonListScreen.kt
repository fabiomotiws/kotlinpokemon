package com.example.pokemonkt.ui.pokemonList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemonkt.domain.model.PokemonListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListRoute(
    onPokemonClick: (PokemonListItem) -> Unit,
    viewModel: PokemonListViewModel = koinViewModel(),
) {
    val state = viewModel.uiState

    PokemonListScreen(
        state = state,
        onRetry = { viewModel.loadPokemon() },
        onPokemonClick = onPokemonClick,
    )
}

@Composable
fun PokemonListScreen(
    state: PokemonListUiState,
    onRetry: () -> Unit,
    onPokemonClick: (PokemonListItem) -> Unit,
) {
    when {
        state.isLoading -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Row(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "Tentar novamente",
                    modifier = Modifier.clickable { onRetry() },
                )
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(state.items) { pokemon ->
                    PokemonRow(
                        pokemon = pokemon,
                        onClick = { onPokemonClick(pokemon) },
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonRow(
    pokemon: PokemonListItem,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.size(64.dp),
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.titleMedium,
        )
    }

    Spacer(modifier = Modifier.height(4.dp))
}
