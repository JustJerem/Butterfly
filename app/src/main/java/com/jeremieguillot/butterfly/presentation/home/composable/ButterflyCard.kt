package com.jeremieguillot.butterfly.presentation.home.composable


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeremieguillot.butterfly.domain.model.ButterflyModel

@Composable
fun ButterflyCard(butterfly: ButterflyModel, onTap: () -> Unit) {

    Card(modifier = Modifier.clickable { onTap() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(butterfly.photos.firstOrNull()).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium),
            )
            Column(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp, start = 8.dp)
                    .fillMaxWidth(),
            ) {

                Text(text = butterfly.commonName, style = typography.titleMedium)
                Text(
                    text = butterfly.latinName,
                    style = typography.labelLarge,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}