@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.jeremieguillot.butterfly.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.FilterVintage
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material.icons.filled.SettingsEthernet
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeremieguillot.butterfly.domain.model.ButterflyModel
import com.jeremieguillot.butterfly.domain.model.ConservationStatus
import com.jeremieguillot.butterfly.domain.model.VisibleMonth
import com.jeremieguillot.butterfly.presentation.destinations.ZoomableScreenDestination
import com.jeremieguillot.butterfly.presentation.detail.composable.ConservationStatusLogo
import com.jeremieguillot.butterfly.presentation.detail.composable.YearlyCalendar
import com.jeremieguillot.butterfly.presentation.home.composable.ButterflyCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailScreen(
    butterfly: ButterflyModel,
    navigator: DestinationsNavigator,
    viewModel: DetailViewModel = hiltViewModel(),
) {

    //TODO quick hack, must be improved.
    var callDone by remember {
        mutableStateOf(true)
    }
    if (callDone) {
        viewModel.checkConfussionButterflies(butterfly)
        callDone = false
    }

    DetailScreen(butterfly, navigator, viewModel.confussionButterflies)
}

@Composable
private fun DetailScreen(
    butterfly: ButterflyModel,
    navigator: DestinationsNavigator,
    confusionButterfly: List<ButterflyModel>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }

            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            item {
                val pagerState = rememberPagerState(pageCount = {
                    butterfly.carousel.size
                })
                Column {


                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) { page ->

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(butterfly.carousel[page])
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable { navigator.navigate(ZoomableScreenDestination(butterfly.carousel[page])) }
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
                ButterflyDetailItem(
                    Icons.Default.Info,
                    "Confusions possibles",
                    butterfly.possibleConfusions
                )

                ButterflyDetailItem(Icons.Default.FamilyRestroom, "Famille", butterfly.family)
                ButterflyDetailStatusItem(
                    Icons.Default.Policy,
                    "Statut de conservation en France",
                    butterfly.conservationStatusFrance
                )
                ButterflyDetailItem(
                    Icons.Default.Policy,
                    "Statut de protection",
                    butterfly.protectionStatus
                )

                ButterflyVisibilityDetailItem(
                    Icons.Default.Visibility,
                    "Période de vol",
                    butterfly.flightPeriod
                )
                ButterflyDetailItem(
                    Icons.Default.FilterVintage,
                    "Habitats naturels",
                    butterfly.naturalHabitats.joinToString(", ")
                )

                ButterflyDetailItem(
                    Icons.Default.FilterVintage,
                    "Plantes hôtes",
                    butterfly.hostPlants.joinToString(", ")
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

                if (butterfly.confusionButterfliesId.isNotEmpty()) {
                    ButterflyConfusionDetailItem(
                        "Confusion possible",
                        confusionButterfly,
                        navigator
                    )

                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
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

@Composable
fun ButterflyVisibilityDetailItem(
    icon: ImageVector,
    description: String,
    visibleMonths: List<VisibleMonth>
) {
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
                modifier = Modifier.padding(bottom = 8.dp),
                text = description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            YearlyCalendar(visibleMonths)
        }
    }
}

@Composable
fun ButterflyConfusionDetailItem(
    description: String,
    butterflies: List<ButterflyModel>,
    navigator: DestinationsNavigator
) {

    Column {
        Text(
            modifier = Modifier.padding(bottom = 8.dp, start = 48.dp),
            text = description,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            contentPadding = PaddingValues(start = 48.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(butterflies) {
                ButterflyCard(
                    modifier = Modifier
                        .width(120.dp),
                    butterfly = it
                ) {
                    //navigator.navigate(DetailScreenDestination(it))
                }
            }
        }
    }
}

@Composable
fun ButterflyDetailStatusItem(icon: ImageVector, description: String, status: ConservationStatus) {
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
            ConservationStatusLogo(status = status)
        }
    }
}
