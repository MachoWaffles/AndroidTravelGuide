package com.example.benchmarkproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.benchmarkproject.models.Places

@Dao
interface PlacesDao {

    @Insert
    fun insertPlace(place: Places)

    @Query("SELECT * FROM places")
    fun getAllPlaces(): List<Places>

    @Query("SELECT * FROM places WHERE id = :id")
    fun getPlaceById(id: Int): Places?

    @Update
    fun updatePlace(place: Places)

    @Delete
    fun deletePlace(place: Places)

    @Query("SELECT * FROM places WHERE name LIKE '%' || :query || '%'")
    fun searchByName(query: String): List<Places>
}