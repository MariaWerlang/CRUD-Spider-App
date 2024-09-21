package com.example.crudapp.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.crudapp.data.Miranha
import com.example.crudapp.data.MiranhasRepository
import java.text.NumberFormat

class MiranhaEntryViewModel(private val miranhasRepository: MiranhasRepository) : ViewModel() {

    var miranhaUiState by mutableStateOf(MiranhaUiState())
        private set


    fun updateUiState(miranhaDetails: MiranhaDetails) {
        miranhaUiState =
            MiranhaUiState(miranhaDetails = miranhaDetails, isEntryValid = validateInput(miranhaDetails))
    }

    private fun validateInput(uiState: MiranhaDetails = miranhaUiState.miranhaDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && earth.toString().isNotBlank()
        }
    }

    suspend fun saveMiranha() {
        if (validateInput()) {
            miranhasRepository.insertMiranha(miranhaUiState.miranhaDetails.toMiranha())
        }
    }
}

data class MiranhaUiState(
    val miranhaDetails: MiranhaDetails = MiranhaDetails(),
    val isEntryValid: Boolean = false
)

data class MiranhaDetails(
    val id: Int = 0,
    val name: String = "",
    val earth: String = ""
)


fun MiranhaDetails.toMiranha(): Miranha = Miranha(
    id = id,
    name = name,
    earth = earth.toIntOrNull() ?: 0
)


fun Miranha.toMiranhaUiState(isEntryValid: Boolean = false): MiranhaUiState = MiranhaUiState(
    miranhaDetails = this.toMiranhaDetails(),
    isEntryValid = isEntryValid
)

fun Miranha.toMiranhaDetails(): MiranhaDetails = MiranhaDetails(
    id = id,
    name = name,
    earth = earth.toString()
)
