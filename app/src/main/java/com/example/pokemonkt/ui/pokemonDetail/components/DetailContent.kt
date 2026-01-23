package com.example.pokemonkt.ui.pokemonDetail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.pokemonkt.domain.model.PokemonDetail
import org.koin.compose.koinInject

@Composable
fun DetailContent(
    pokemon: PokemonDetail,
    modifier: Modifier = Modifier,
    onGoToTest: () -> Unit
) {
    val imageLoader: ImageLoader = koinInject()

    var showGif by rememberSaveable(pokemon.id) { mutableStateOf(false) }

    val hasGif = !pokemon.showdownGifUrl.isNullOrBlank()


    Column(modifier = modifier) {

        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Botão (toggle) no topo direito
                IconButton(
                    onClick = { if (hasGif) showGif = !showGif },
                    enabled = hasGif,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (showGif) Icons.Default.AccountBox else Icons.Default.PlayArrow,
                        contentDescription = if (showGif) "Ocultar animação" else "Mostrar animação"
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = pokemon.artworkUrl,
                        contentDescription = pokemon.name,
                        modifier = Modifier.size(220.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = pokemon.name.replaceFirstChar { it.titlecase() },
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                this@Column.AnimatedVisibility(
                    visible = showGif && hasGif,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    enter = fadeIn() + scaleIn(initialScale = 0.9f),
                    exit = fadeOut() + scaleOut(targetScale = 0.9f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SubcomposeAsyncImage(
                            model = pokemon.showdownGifUrl,
                            imageLoader = imageLoader,
                            contentDescription = "${pokemon.name} animado",
                            modifier = Modifier.size(80.dp),
                            contentScale = ContentScale.Fit
                        ) {
                            when (painter.state) {
                                is coil.compose.AsyncImagePainter.State.Loading -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(strokeWidth = 2.dp)
                                    }
                                }

                                is coil.compose.AsyncImagePainter.State.Error -> {
                                    // fallback simples: mostra texto ou ícone
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Sem animação",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                else -> {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            modifier = Modifier.clickable{onGoToTest()},
            text = "Sobre",
            style = MaterialTheme.typography.titleMedium,
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