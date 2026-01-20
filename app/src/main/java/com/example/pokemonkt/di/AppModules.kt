package com.example.pokemonkt.di

import com.example.pokemonkt.data.remote.PokeApiService
import com.example.pokemonkt.data.repository.PokemonRepositoryImpl
import com.example.pokemonkt.domain.repository.PokemonRepository
import com.example.pokemonkt.ui.pokemonDetail.PokemonDetailViewModel
import com.example.pokemonkt.ui.pokemonList.PokemonListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://pokeapi.co/api/v2/"

val appModule =
    module {

        // OkHttp
        single {
            val logging =
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .build()
        }

        // Retrofit
        single {
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        // Service
        single {
            get<Retrofit>().create(PokeApiService::class.java)
        }

        // Repository
        single<PokemonRepository> {
            PokemonRepositoryImpl(api = get())
        }

        // ViewModel
        viewModel {
            PokemonListViewModel(repository = get())
        }

        viewModel {
            PokemonDetailViewModel(repository = get())
        }
    }
