@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.jeremieguillot.butterfly.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jeremieguillot.butterfly.domain.model.ButterflyColor
import com.jeremieguillot.butterfly.domain.model.ConservationStatus
import com.jeremieguillot.butterfly.domain.model.Displayable
import com.jeremieguillot.butterfly.domain.model.Frequency
import com.jeremieguillot.butterfly.domain.model.HabitatType
import com.jeremieguillot.butterfly.domain.model.MonthEnum
import com.jeremieguillot.butterfly.domain.model.ProtectionStatus
import com.jeremieguillot.butterfly.domain.model.Region
import com.jeremieguillot.butterfly.presentation.detail.composable.ConservationStatusLogo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun FilterScreen(
    filterOptions: FilterOptions,
    onApplyFilter: (FilterOptions) -> Unit,
    navigator: DestinationsNavigator,
    onDismiss: () -> Unit,
) {

    var selectedFlightMonths by remember { mutableStateOf<List<MonthEnum>>(emptyList()) }
    var selectedConservationStatus by remember { mutableStateOf<List<ConservationStatus>>(emptyList()) }
    var selectedHabitatType by remember { mutableStateOf<List<HabitatType>>(emptyList()) }
    var selectedProtectionStatus by remember { mutableStateOf<List<ProtectionStatus>>(emptyList()) }
    var selectedFrequencies by remember { mutableStateOf<List<Frequency>>(emptyList()) }
    var selectedRegions by remember { mutableStateOf<List<Region>>(emptyList()) }
    var selectedColors by remember { mutableStateOf<List<ButterflyColor>>(emptyList()) }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Filtrer",
                        style = MaterialTheme.typography.titleMedium
                    )
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
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Bouton de réinitialisation
                OutlinedButton(
                    onClick = {
                        selectedFlightMonths = emptyList()
                        selectedConservationStatus = emptyList()
                        selectedHabitatType = emptyList()
                        selectedProtectionStatus = emptyList()
                        selectedFrequencies = emptyList()
                        selectedRegions = emptyList()
                        selectedColors = emptyList()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                ) {
                    Text(text = "Réinitialiser")
                }

                // Bouton d'application des filtres
                Button(
                    onClick = { /*applyFilters()*/ },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Appliquer")
                }
            }
        }) {

        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
        ) {

            item {
                FilterSection("Frequence", Frequency.values(), selectedFrequencies) {
                    selectedFrequencies = updateSelectedItem(it, selectedFrequencies)
                }
            }

            item {
                FilterSection("Conservation", ProtectionStatus.values(), selectedProtectionStatus) {
                    selectedProtectionStatus = updateSelectedItem(it, selectedProtectionStatus)
                }
            }

            item {
                SectionTitle("Status")
            }

            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ConservationStatus.values().forEach { status ->
                        FilterChip(
                            label = { ConservationStatusLogo(status) },
                            selected = selectedConservationStatus.contains(status),
                            onClick = {
                                selectedConservationStatus =
                                    updateSelectedItem(status, selectedConservationStatus)
                            }
                        )
                    }
                }
            }

            item {
                FilterSection(
                    "Couleurs",
                    ButterflyColor.values().sortedBy { it.name }.toTypedArray(),
                    selectedColors
                ) {
                    selectedColors = updateSelectedItem(it, selectedColors)
                }
            }


            item {
                SectionTitle("Periode de vol")
            }

            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MonthEnum.values().forEach { month ->
                        FilterChip(
                            label = { Text(text = stringResource(id = month.shortName)) },
                            selected = selectedFlightMonths.contains(month),
                            onClick = {
                                selectedFlightMonths =
                                    updateSelectedItem(month, selectedFlightMonths)
                            }
                        )
                    }
                }
            }

            item {
                FilterSection(
                    "Regions",
                    Region.values().sortedBy { it.name }.toTypedArray(),
                    selectedRegions
                ) {
                    selectedRegions = updateSelectedItem(it, selectedRegions)
                }
            }

            item {
                FilterSection(
                    "Habitat naturel",
                    HabitatType.values().sortedBy { it.name }.toTypedArray(),
                    selectedHabitatType
                ) {
                    selectedHabitatType = updateSelectedItem(it, selectedHabitatType)
                }
            }
        }
    }
}

@Composable
fun <T : Displayable> FilterSection(
    title: String,
    items: Array<T>,
    selectedItems: List<T>,
    onItemSelected: (T) -> Unit
) {

    SectionTitle(title)
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        items.forEach { item ->
            FilterChip(
                label = { Text(text = item.displayName) },
                selected = selectedItems.contains(item),
                onClick = { onItemSelected(item) }
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
    )
}


fun <T> updateSelectedItem(item: T, selectedItems: List<T>): List<T> {
    return if (selectedItems.contains(item)) {
        selectedItems - item
    } else {
        selectedItems + item
    }
}

@Composable
@Destination
fun FilterScreenPreview(
    navigator: DestinationsNavigator
) {
    val filterOptions = FilterOptions()
    val onApplyFilter: (FilterOptions) -> Unit = { /* Handle filter application */ }
    val onDismiss: () -> Unit = { /* Handle filter dismissal */ }

    MaterialTheme {
        Surface {
            FilterScreen(
                filterOptions = filterOptions,
                onApplyFilter = onApplyFilter,
                navigator = navigator,
                onDismiss = onDismiss
            )
        }
    }
}

// Modèle de données pour les options de filtrage
class FilterOptions
