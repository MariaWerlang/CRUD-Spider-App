package com.example.crudapp.data

import kotlinx.coroutines.flow.Flow

interface MiranhasRepository{
    fun getAllMiranhasStream(): Flow<List<Miranha>>

    fun getMiranhaStream(id: Int): Flow<Miranha?>

    suspend fun insertMiranha(miranha: Miranha)

    suspend fun deleteMiranha(miranha: Miranha)

    suspend fun updateMiranha(miranha: Miranha)
}