@file:OptIn(ExperimentalFoundationApi::class)

package com.jeremieguillot.butterfly.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.FilterVintage
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material.icons.filled.SettingsEthernet
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DetailScreen(
    butterfly: ButterflyModel
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            val pagerState = rememberPagerState(pageCount = {
                butterfly.carousel.size
            })
            Column {


                HorizontalPager(state = pagerState) { page ->


                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(butterfly.carousel[page])
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.medium),
                    )
                }

                Box {
                    Row(
                        Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(pagerState.pageCount) { iteration ->
                            val color =
                                if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(20.dp)

                            )
                        }
                    }
                }

            }
        }
        item {
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = butterfly.commonName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            ButterflyDetailItem(Icons.Default.Language, "Nom latin", butterfly.latinName)
            ButterflyDetailItem(Icons.Default.FamilyRestroom, "Famille", butterfly.family)
            ButterflyDetailItem(
                Icons.Default.Policy,
                "Statut de conservation en France",
                butterfly.conservationStatusFrance
            )
            ButterflyDetailItem(
                Icons.Default.Policy,
                "Statut de protection",
                butterfly.protectionStatus
            )

            ButterflyDetailItem(
                Icons.Default.Visibility,
                "Période de vol",
                butterfly.flightPeriod.joinToString(", ")
            )
            ButterflyDetailItem(
                Icons.Default.FilterVintage,
                "Habitats naturels",
                butterfly.naturalHabitats.joinToString(", ")
            )

            ButterflyDetailItem(
                Icons.Default.Info,
                "Confusions possibles",
                butterfly.possibleConfusions
            )
            ButterflyDetailItem(Icons.Default.Info, "Fréquence", butterfly.frequency)
            ButterflyDetailItem(
                Icons.Default.PregnantWoman,
                "Générations par an",
                butterfly.generationsPerYear.toString()
            )
            ButterflyDetailItem(
                Icons.Default.SettingsEthernet,
                "Envergure des ailes",
                "${butterfly.minWingspan} mm - ${butterfly.maxWingspan} mm"
            )

            ButterflyDetailItem(
                Icons.Default.Terrain,
                "Altitude",
                "${butterfly.minAltitude} m - ${butterfly.maxAltitude} m"
            )

            ButterflyDetailItem(
                Icons.Default.AcUnit,
                "Stade hivernal",
                butterfly.winteringStage.joinToString(", ")
            )
            ButterflyDetailItem(
                Icons.Default.ImageSearch,
                "Auteur de la photo",
                butterfly.photoAuthor
            )
        }
    }
}

@Composable
fun ButterflyDetailItem(icon: ImageVector, description: String, info: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.wrapContentSize(),
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray
        )
        Spacer(
            modifier = Modifier
                .width(16.dp)
                .background(Color.Red)
        )
        Column {
            Text(
                text = description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = info,
            )
        }
    }
}
