@file:OptIn(ExperimentalMaterial3Api::class)

package com.jeremieguillot.butterfly.presentation.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeremieguillot.butterfly.presentation.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Composable
@RootNavGraph(start = true)
@Destination
fun CategoryScreen(navigator: DestinationsNavigator) {
    val categories = listOf(
        "Papilionidés",
        "Lycénidés",
        "Nymphalidés",
        "Riodinidés",
        "Hespéridés",
        "Piéridés"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(8.dp)
                )
            })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {


            LazyColumn {
                items(categories) { category ->
                    CategoryItem(category = category) {
                        navigator.navigate(HomeScreenDestination(it))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: String, onCategorySelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCategorySelected(category) }
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryScreen(EmptyDestinationsNavigator)
}
