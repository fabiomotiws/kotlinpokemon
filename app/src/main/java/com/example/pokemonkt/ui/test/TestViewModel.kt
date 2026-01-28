package com.example.pokemonkt.ui.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel: ViewModel() {
    var uiState by mutableStateOf(TestUiState(isLoading = true))
        private set

    init {
        load()
    }

    fun load(){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            delay(1200L)

            uiState = uiState.copy(isLoading = false)
        }
    }
}