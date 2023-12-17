package com.jeremieguillot.butterfly.presentation.detail.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeremieguillot.butterfly.R
import com.jeremieguillot.butterfly.domain.model.ButterflyModel

@Composable
fun ButterflyConfusionCard(butterfly: ButterflyModel) {
    Card(
        modifier = Modifier
            .height(180.dp)
            .width(120.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            val holder = painterResource(R.drawable.ic_launcher_foreground)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(butterfly.thumbnail.filePath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = holder,
                error = holder,
                fallback = holder,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(),
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(2.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = butterfly.commonName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = butterfly.latinName,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}