package com.example.benchmarkproject.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

class ScreenInfo(
    val routeName: String,
    val title: String = "",
    val navIcon: ImageVector,
    val composable: @Composable () -> Unit = {}
)