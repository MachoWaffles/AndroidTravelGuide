package com.example.benchmarkproject

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.benchmarkproject.database.PlacesDatabase
import com.example.benchmarkproject.models.Places
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlacesViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = PlacesDatabase.getInstance(application).placesDao()

    var places = mutableStateListOf<Places>()
        private set

    var filteredPlaces = mutableStateListOf<Places>()
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (dao.getAllPlaces().isEmpty()) {
                dao.insertPlace(
                    Places(
                        name = "My Dorm",
                        category = "Dorm",
                        description = "Cool place to chill and relax.",
                        visited = true,
                        rating = 5,
                        images = listOf("test_shot"),
                        location = "33.4772, -112.0733"
                    )
                )
                dao.insertPlace(
                    Places(
                        name = "Halo Parking Garage",
                        category = "Nights",
                        description = "Great view of the city at night.",
                        visited = false,
                        rating = 3,
                        images = listOf("outdoor_scene"),
                        location = "33.4800, -112.0750"
                    )
                )
                dao.insertPlace(
                    Places(
                        name = "Esports Lounge",
                        category = "Gaming",
                        description = "Fun place to practice and compete.",
                        visited = true,
                        rating = 4,
                        images = listOf("test_shot"),
                        location = "33.4755, -112.0710"
                    )
                )
            }
            refreshPlaces()
        }
    }

    private suspend fun refreshPlaces() {
        val all = dao.getAllPlaces()
        withContext(Dispatchers.Main) {
            places.clear()
            places.addAll(all)
        }
    }

    fun addPlace(place: Places) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertPlace(place)
            refreshPlaces()
        }
    }

    fun updatePlace(index: Int, updated: Places) {
        viewModelScope.launch(Dispatchers.IO) {
            val withId = updated.copy(id = places[index].id)
            dao.updatePlace(withId)
            refreshPlaces()
        }
    }

    fun searchPlaces(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val results = dao.searchByName(query)
            withContext(Dispatchers.Main) {
                filteredPlaces.clear()
                filteredPlaces.addAll(results)
            }
        }
    }
}