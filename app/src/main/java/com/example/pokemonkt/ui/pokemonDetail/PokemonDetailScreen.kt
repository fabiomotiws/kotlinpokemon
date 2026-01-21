package com.example.pokemonkt.ui.pokemonDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.pokemonkt.domain.model.PokemonDetail
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import kotlin.math.roundToInt

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
        onBack = onBack,
        onRetry = { viewModel.load(pokemonName) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    state: PokemonDetailUiState,
    onBack: () -> Unit,
    onRetry: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingDetail(modifier = Modifier.padding(padding))
            }

            state.errorMessage != null -> {
                ErrorDetail(
                    message = state.errorMessage,
                    onBack = onBack,
                    onRetry = onRetry,
                    modifier = Modifier.padding(padding)
                )
            }

            state.data != null -> {
                DetailContent(
                    pokemon = state.data,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun LoadingDetail(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(Modifier.height(12.dp))
        Text("Carregando detalhes…")
    }
}

@Composable
private fun ErrorDetail(
    message: String,
    onBack: () -> Unit,
    onRetry: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (onRetry != null) {
                Button(onClick = onRetry) { Text("Tentar novamente") }
            }
            OutlinedButton(onClick = onBack) { Text("Voltar") }
        }
    }
}

@Composable
private fun DetailContent(
    pokemon: PokemonDetail,
    modifier: Modifier = Modifier
) {
    val imageLoader: ImageLoader = koinInject()
    Column(modifier = modifier) {

        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = pokemon.imageUrl,
                    imageLoader = imageLoader,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(220.dp)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = pokemon.name.replaceFirstChar { it.titlecase() },
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Sobre",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                title = "Altura",
                value = pokemon.height.toString() + " dm",
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Peso",
                value = pokemon.weight.toString() + " hg",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(6.dp))
            Text(text = value, style = MaterialTheme.typography.titleLarge)
        }
    }
}
