package com.example.crudapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MiranhaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(miranha: Miranha)

    @Update
    suspend fun update(miranha: Miranha)

    @Delete
    suspend fun delete(miranha: Miranha)

    @Query("SELECT * from miranha WHERE id = :id")
    fun getMiranha(id: Int): Flow<Miranha>

    @Query("SELECT * from miranha ORDER BY name ASC")
    fun getAllMiranhas(): Flow<List<Miranha>>

}