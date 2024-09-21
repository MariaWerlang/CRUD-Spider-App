package com.example.crudapp.data

import kotlinx.coroutines.flow.Flow

class OfflineMiranhasRepository(private val miranhaDAO: MiranhaDAO) : MiranhasRepository{
    override fun getAllMiranhasStream(): Flow<List<Miranha>> = miranhaDAO.getAllMiranhas()

    override fun getMiranhaStream(id: Int): Flow<Miranha?> = miranhaDAO.getMiranha(id)

    override suspend fun insertMiranha(miranha: Miranha) = miranhaDAO.insert(miranha)

    override suspend fun deleteMiranha(miranha: Miranha) = miranhaDAO.delete(miranha)

    override suspend fun updateMiranha(miranha: Miranha) = miranhaDAO.update(miranha)
}