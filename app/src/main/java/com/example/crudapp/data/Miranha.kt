package com.example.crudapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "miranha")
data class Miranha(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val earth: Int
)