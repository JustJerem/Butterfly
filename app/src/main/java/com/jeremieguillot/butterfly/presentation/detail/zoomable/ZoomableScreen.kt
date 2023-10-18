@file:OptIn(ExperimentalFoundationApi::class)

package com.jeremieguillot.butterfly.presentation.detail.zoomable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun ZoomableScreen(
    imagePath: String,
    navigator: DestinationsNavigator
) {
    ZoomableImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imagePath)
            .crossfade(true)
            .build()
    ) {
        navigator.popBackStack()
    }
}