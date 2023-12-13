@file:OptIn(ExperimentalFoundationApi::class)

package com.jeremieguillot.butterfly.presentation.detail.zoomable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.jeremieguillot.butterfly.domain.model.ImageInfo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun ZoomableScreen(
    imageInfo: ImageInfo,
    navigator: DestinationsNavigator
) {
    ZoomableImage(
        imageInfo
    ) {
        navigator.popBackStack()
    }
}