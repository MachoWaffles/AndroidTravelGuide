package com.example.benchmarkproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.benchmarkproject.models.Places

@Database(entities = [Places::class], version = 1)
@TypeConverters(PlacesTypeConverter::class)
abstract class PlacesDatabase : RoomDatabase() {

    abstract fun placesDao(): PlacesDao

    companion object {
        @Volatile
        private var INSTANCE: PlacesDatabase? = null

        fun getInstance(context: Context): PlacesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlacesDatabase::class.java,
                    "places_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}