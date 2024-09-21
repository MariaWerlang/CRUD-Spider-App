package com.example.crudapp.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudapp.data.MiranhasRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MiranhaEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val miranhasRepository: MiranhasRepository
) : ViewModel() {
    var miranhaUiState by mutableStateOf(MiranhaUiState())
        private set

    private val miranhaId: Int = checkNotNull(savedStateHandle[MiranhaEditDestination.miranhaIdArg])

    init {
        viewModelScope.launch{
            miranhaUiState = miranhasRepository.getMiranhaStream(miranhaId)
                .filterNotNull()
                .first()
                .toMiranhaUiState(true)
        }
    }


    private fun validateInput(uiState: MiranhaDetails = miranhaUiState.miranhaDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && earth.isNotBlank()
        }
    }

    fun updateUiState(miranhaDetails: MiranhaDetails) {
        miranhaUiState =
            MiranhaUiState(miranhaDetails = miranhaDetails, isEntryValid = validateInput(miranhaDetails))
    }

    suspend fun updateMiranha() {
        if (validateInput(miranhaUiState.miranhaDetails)) {
            miranhasRepository.updateMiranha(miranhaUiState.miranhaDetails.toMiranha())
        }
    }
}
