package com.example.crudapp.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudapp.data.MiranhasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MiranhaDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val miranhasRepository: MiranhasRepository
) : ViewModel() {

    private val miranhaId: Int = checkNotNull(savedStateHandle[MiranhaDetailsDestination.miranhaIdArg])

    val uiState: StateFlow<MiranhaDetailsUiState> =
        miranhasRepository.getMiranhaStream(miranhaId)
            .filterNotNull()
            .map {
                MiranhaDetailsUiState(miranhaDetails = it.toMiranhaDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MiranhaDetailsUiState()
            )


    suspend fun deleteMiranha() {
        miranhasRepository.deleteMiranha(uiState.value.miranhaDetails.toMiranha())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class MiranhaDetailsUiState(
    val miranhaDetails: MiranhaDetails = MiranhaDetails()
)