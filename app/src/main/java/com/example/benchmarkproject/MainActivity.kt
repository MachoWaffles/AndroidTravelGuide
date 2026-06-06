package com.example.benchmarkproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.benchmarkproject.navigation.Screen
import com.example.benchmarkproject.screens.AddEditPlaceScreen
import com.example.benchmarkproject.screens.MapScreen
import com.example.benchmarkproject.screens.PlaceDetailScreen
import com.example.benchmarkproject.screens.PlacesListScreen
import com.example.benchmarkproject.screens.WelcomeScreen
import com.example.benchmarkproject.ui.theme.BenchmarkProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BenchmarkProjectTheme {
                MyApp()
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun MyApp() {
    val navController = rememberNavController()
    val viewModel: PlacesViewModel = viewModel()
    NavigationGraph(navController = navController, viewModel = viewModel)
}

@androidx.compose.runtime.Composable
fun NavigationGraph(navController: NavHostController, viewModel: PlacesViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(Screen.PlacesList.route) {
            PlacesListScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = Screen.PlaceDetail.route,
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            PlaceDetailScreen(
                navController = navController,
                place = viewModel.places[index],
                index = index
            )
        }

        composable(
            route = Screen.AddEditPlace.route,
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditPlaceScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.Map.route) {
            MapScreen(navController = navController, viewModel = viewModel)
        }
    }
}