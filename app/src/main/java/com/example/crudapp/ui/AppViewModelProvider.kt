package com.example.crudapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.crudapp.CRUDranhaApplication
import com.example.crudapp.ui.home.HomeViewModel
import com.example.crudapp.ui.item.MiranhaDetailsViewModel
import com.example.crudapp.ui.item.MiranhaEditViewModel
import com.example.crudapp.ui.item.MiranhaEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MiranhaEditViewModel(
                this.createSavedStateHandle(),
                crudApplication().container.miranhasRepository
            )
        }

        initializer {
            MiranhaEntryViewModel(crudApplication().container.miranhasRepository)
        }

        initializer {
            MiranhaDetailsViewModel(
                this.createSavedStateHandle(),
                crudApplication().container.miranhasRepository
            )
        }

        initializer {
            HomeViewModel(crudApplication().container.miranhasRepository)
        }
    }
}

fun CreationExtras.crudApplication(): CRUDranhaApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CRUDranhaApplication)