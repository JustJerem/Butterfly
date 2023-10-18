@file:OptIn(ExperimentalLayoutApi::class)

package com.jeremieguillot.butterfly.presentation.detail.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.md_theme_light_tertiaryContainer
import com.jeremieguillot.butterfly.domain.model.VisibleMonth

@Composable
fun YearlyCalendar(visibleMonths: List<VisibleMonth>) {
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        visibleMonths.forEach { month ->
            MonthCard(visibleMonth = month)
        }
    }
}

@Composable
fun MonthCard(visibleMonth: VisibleMonth) {
    ElevatedCard(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (visibleMonth.isValidated) md_theme_light_tertiaryContainer else MaterialTheme.colorScheme.surface,
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.width(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = visibleMonth.nameRes),
                color = Color.Black,
            )
        }
    }
}