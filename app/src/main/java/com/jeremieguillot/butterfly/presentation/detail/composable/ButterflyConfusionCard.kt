package com.jeremieguillot.butterfly.presentation.detail.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeremieguillot.butterfly.domain.model.ButterflyModel

@Composable
fun ButterflyConfusionCard(butterfly: ButterflyModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(butterfly.photos.first())
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .width(60.dp)
                    .clip(MaterialTheme.shapes.medium),
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = butterfly.commonName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = butterfly.latinName,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}