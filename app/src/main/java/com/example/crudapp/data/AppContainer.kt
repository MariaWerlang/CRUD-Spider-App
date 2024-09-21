package com.example.crudapp.data

import android.content.Context

interface AppContainer {
    val miranhasRepository: MiranhasRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val miranhasRepository: MiranhasRepository by lazy {
        OfflineMiranhasRepository(DBase.getDatabase(context).miranhaDao())
    }
}