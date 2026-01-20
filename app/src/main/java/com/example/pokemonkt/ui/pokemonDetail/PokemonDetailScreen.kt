package com.example.pokemonkt.ui.pokemonDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailRoute(
    pokemonName: String,
    onBack: () -> Unit,
    viewModel: PokemonDetailViewModel = koinViewModel()
) {
    LaunchedEffect(pokemonName) {
        viewModel.load(pokemonName)
    }

    PokemonDetailScreen(
        state = viewModel.uiState,
        onBack = onBack
    )
}

@Composable
fun PokemonDetailScreen(
    state: PokemonDetailUiState,
    onBack: () -> Unit
) {
    when {
        state.isLoading -> {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Voltar", modifier = Modifier.padding(8.dp))
            }
        }

        state.data != null -> {
            val p = state.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Voltar",
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                AsyncImage(
                    model = p.imageUrl,
                    contentDescription = p.name,
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = p.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Altura: ${p.height}")
                Text(text = "Peso: ${p.weight}")
            }
        }
    }
}
