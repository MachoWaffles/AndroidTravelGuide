package com.example.benchmarkproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Places(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val description: String,
    val visited: Boolean,
    val rating: Int,
    val images: List<String> = emptyList(),
    val location: String
)