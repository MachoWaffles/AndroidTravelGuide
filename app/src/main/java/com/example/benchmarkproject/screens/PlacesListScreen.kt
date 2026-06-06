package com.example.benchmarkproject.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.benchmarkproject.PlacesViewModel
import com.example.benchmarkproject.components.PlaceSlide
import com.example.benchmarkproject.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesListScreen(navController: NavHostController, viewModel: PlacesViewModel) {
    var query by remember { mutableStateOf("") }

    val displayList = if (query.isBlank()) viewModel.places else viewModel.filteredPlaces

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Favorite Places", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Home",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Map.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Map",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditPlace.createRoute()) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add Place",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.searchPlaces(it)
                },
                label = { Text("Search by name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(displayList) { place ->
                    val actualIndex = viewModel.places.indexOf(place)
                    PlaceSlide(
                        place = place,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.PlaceDetail.createRoute(actualIndex))
                        }
                    )
                }
            }
        }
    }
}