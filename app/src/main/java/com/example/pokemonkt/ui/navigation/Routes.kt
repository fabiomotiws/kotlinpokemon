package com.example.pokemonkt.ui.navigation

object Routes {
    const val POKEMON_LIST = "pokemonList"
    const val POKEMON_DETAIL = "pokemonDetail"

    const val TEST = "test"

    const val ARG_NAME = "name"

    fun detailRoutePattern(): String = "$POKEMON_DETAIL/{$ARG_NAME}"

    fun detailRoute(name: String): String = "$POKEMON_DETAIL/$name"
}
