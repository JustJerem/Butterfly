@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.jeremieguillot.butterfly.presentation.detail.zoomable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jeremieguillot.butterfly.domain.model.ImageInfo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
@Destination
fun ZoomableScreen(
    imageInfo: ImageInfo,
    navigator: DestinationsNavigator
) {

    //TODO
    // 1.synchroniser le projet
    // 2.voir si les images s'affichent bien
    // 3. changer la date de prise de photo pour un vrai text

    Scaffold(topBar = {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(
                onClick = { navigator.popBackStack() },
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Close full screen",
                    tint = Color.White
                )
            }
        })
    }) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {

            Text(
                text = imageInfo.captureDate.toString(),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            )


            AsyncImage(
                imageInfo.filePath,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .zoomable(rememberZoomState())
            )
        }
    }


//    ZoomableImage(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(imagePath)
//            .crossfade(true)
//            .build()
//    ) {
//        navigator.popBackStack()
//    }
}