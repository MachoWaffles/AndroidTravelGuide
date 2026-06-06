package com.example.benchmarkproject.database

import androidx.room.TypeConverter

class PlacesTypeConverter {

    @TypeConverter
    fun fromList(images: List<String>): String {
        return images.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        return if (data.isBlank()) emptyList() else data.split(",")
    }
}