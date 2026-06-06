package com.example.benchmarkproject.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object PlacesList : Screen("places-list")
    object PlaceDetail : Screen("place-detail/{index}") {
        fun createRoute(index: Int) = "place-detail/$index"
    }
    object AddEditPlace : Screen("add-edit-place?index={index}") {
        fun createRoute(index: Int = -1) = "add-edit-place?index=$index"
    }
    object Map : Screen("map")
}