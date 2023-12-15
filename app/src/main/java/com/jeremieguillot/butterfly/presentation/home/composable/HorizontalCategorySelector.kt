package com.jeremieguillot.butterfly.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun HorizontalCategoryList(
    onCategorySelected: (ButterflyCategory) -> Unit
) {
    val categories = ButterflyCategory.values()
    var selectedCategory by remember { mutableStateOf(categories.first()) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onCategorySelected = {
                    selectedCategory = category
                    onCategorySelected(category)
                }
            )
        }
    }
}

@Composable
fun CategoryItem(category: ButterflyCategory, isSelected: Boolean, onCategorySelected: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onCategorySelected() }
            .background(if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category.displayName,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}

enum class ButterflyCategory(val displayName: String) {
    PAPILIONIDES("Papilionidés"),
    LYCENIDES("Lycénidés"),
    NYMPHALIDES("Nymphalidés"),
    RIODINIDES("Riodinidés"),
    HESPERIDES("Hespéridés"),
    PIERIDES("Piéridés")
}