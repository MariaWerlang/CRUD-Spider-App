package com.example.crudapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudapp.data.Miranha
import com.example.crudapp.data.MiranhasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(miranhasRepository: MiranhasRepository) : ViewModel() {

    val homeUiState :StateFlow<HomeUiState> =
        miranhasRepository.getAllMiranhasStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val miranhaList: List<Miranha> = listOf())