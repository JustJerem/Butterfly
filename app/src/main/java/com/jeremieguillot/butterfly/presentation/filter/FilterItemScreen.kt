@file:OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)

package com.jeremieguillot.butterfly.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeremieguillot.butterfly.presentation.home.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@OptIn(ExperimentalLayoutApi::class)
@Destination
@Composable
@RootNavGraph
fun FilterItemScreen(
    categoryType: CategoryType,
    homeViewModel: HomeViewModel
) {


    val list = homeViewModel.getSelectedItems()


    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        list.forEach { item ->
            FilterChip(
                label = { Text(text = item.name) },
                selected = item.selected,
                onClick = { homeViewModel.onItemSelected(item) }
            )
        }
    }
}

data class FilterItem(val name: String, val selected: Boolean, val type: CategoryType)