package com.example.pokemonkt

import android.app.Application
import com.example.pokemonkt.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokemonApp)
            modules(appModule)
        }
    }
}
