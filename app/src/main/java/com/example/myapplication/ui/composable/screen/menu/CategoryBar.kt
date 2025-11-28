package com.example.myapplication.ui.composable.screen.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryBar(
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit
) {
    val categories = listOf("Entrantes", "Platos", "Bebidas", "Menús", "Promociones", "Postres")

    ScrollableTabRow(
        selectedTabIndex = selectedCategory,
        modifier = Modifier.padding(horizontal = 4.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        edgePadding = 0.dp,
        divider = {},
        indicator = { tabPositions ->
            if (selectedCategory < tabPositions.size) {
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedCategory])
                        .padding(horizontal = 16.dp),
                    height = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) {
        categories.forEachIndexed { index, title ->
            Tab(
                selected = selectedCategory == index,
                onClick = { onCategorySelected(index) },
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        color = if (selectedCategory == index)
                            MaterialTheme.colorScheme.primary
                        else
                            LocalContentColor.current.copy(alpha = 0.60f)
                    )
                }
            )
        }
    }
}