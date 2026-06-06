package com.example.benchmarkproject.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.benchmarkproject.PlacesViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

private fun parseGeoPoint(location: String): GeoPoint? {
    return try {
        val parts = location.split(",")
        if (parts.size == 2) {
            val lat = parts[0].trim().toDouble()
            val lng = parts[1].trim().toDouble()
            if (lat in -90.0..90.0 && lng in -180.0..180.0) GeoPoint(lat, lng) else null
        } else null
    } catch (e: NumberFormatException) {
        null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavHostController, viewModel: PlacesViewModel) {
    val context = LocalContext.current
    val places = viewModel.places

    val placeMarkers = remember(places.size) {
        places.mapNotNull { place ->
            parseGeoPoint(place.location)?.let { point -> place to point }
        }
    }

    val centerPoint = if (placeMarkers.isNotEmpty()) {
        val avgLat = placeMarkers.map { it.second.latitude }.average()
        val avgLng = placeMarkers.map { it.second.longitude }.average()
        GeoPoint(avgLat, avgLng)
    } else {
        GeoPoint(33.4484, -112.0740)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Map View", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { ctx ->
                Configuration.getInstance().userAgentValue = ctx.packageName
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(13.0)
                    controller.setCenter(centerPoint)

                    placeMarkers.forEach { (place, point) ->
                        val marker = Marker(this)
                        marker.position = point
                        marker.title = place.name
                        marker.snippet = place.category
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        overlays.add(marker)
                    }
                }
            }
        )
    }
}