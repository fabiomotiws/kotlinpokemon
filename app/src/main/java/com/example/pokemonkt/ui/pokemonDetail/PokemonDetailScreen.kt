package com.example.pokemonkt.ui.pokemonDetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemonkt.ui.pokemonDetail.components.DetailContent
import com.example.pokemonkt.ui.pokemonDetail.components.states.ErrorDetail
import com.example.pokemonkt.ui.pokemonDetail.components.states.LoadingDetail
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailRoute(
    pokemonName: String,
    onBack: () -> Unit,
    viewModel: PokemonDetailViewModel = koinViewModel(),
    onGoToTest: () -> Unit
) {
    LaunchedEffect(pokemonName) {
        viewModel.load(pokemonName)
    }

    PokemonDetailScreen(
        state = viewModel.uiState,
        onBack = onBack,
        onRetry = { viewModel.load(pokemonName) },
        onGoToTest = onGoToTest
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    state: PokemonDetailUiState,
    onBack: () -> Unit,
    onRetry: (() -> Unit)? = null,
    onGoToTest: () -> Unit
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
                        .padding(16.dp),
                    onGoToTest = onGoToTest
                )
            }
        }
    }
}
