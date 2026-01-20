package com.example.pokemonkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pokemonkt.ui.navigation.AppNavGraph
import com.example.pokemonkt.ui.theme.PokemonKTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*        enableEdgeToEdge()*/
        setContent {
            PokemonKTTheme {
                AppNavGraph()
            }
        }
    }
}
