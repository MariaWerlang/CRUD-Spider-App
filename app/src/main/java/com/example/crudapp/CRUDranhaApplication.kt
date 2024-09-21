package com.example.crudapp

import android.app.Application
import com.example.crudapp.data.AppContainer
import com.example.crudapp.data.AppDataContainer

class CRUDranhaApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}