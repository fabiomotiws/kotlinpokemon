package com.example.pokemonkt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonkt.ui.pokemonDetail.PokemonDetailRoute
import com.example.pokemonkt.ui.pokemonList.PokemonListRoute


@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
            navController = navController,
            startDestination = Routes.POKEMON_LIST
    ) {
        composable(route = Routes.POKEMON_LIST) {
            PokemonListRoute(
                    onPokemonClick = { pokemon ->
                            navController.navigate(Routes.detailRoute(pokemon.name))
                    }
            )
        }

        composable(
                route = Routes.detailRoutePattern(),
                arguments = listOf(navArgument(Routes.ARG_NAME) { type = NavType.StringType })
        ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString(Routes.ARG_NAME)
                ?: return@composable

                PokemonDetailRoute(
                pokemonName = name,
                onBack = { navController.popBackStack() }
        )
        }
    }
}
