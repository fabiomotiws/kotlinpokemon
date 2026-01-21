package com.example.pokemonkt.di

import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.pokemonkt.data.remote.PokeApiService
import com.example.pokemonkt.data.repository.PokemonRepositoryImpl
import com.example.pokemonkt.domain.repository.PokemonRepository
import com.example.pokemonkt.ui.pokemonDetail.PokemonDetailViewModel
import com.example.pokemonkt.ui.pokemonList.PokemonListViewModel
import com.example.pokemonkt.utils.constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



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

        //ImageLoader para exibir o gif
        single {
            ImageLoader.Builder(androidContext())
                .components {
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()
        }

        // Repository
        single<PokemonRepository> {
            PokemonRepositoryImpl(api = get())
        }

        // ViewModels
        viewModel {
            PokemonListViewModel(repository = get())
        }

        viewModel {
            PokemonDetailViewModel(repository = get())
        }
    }
