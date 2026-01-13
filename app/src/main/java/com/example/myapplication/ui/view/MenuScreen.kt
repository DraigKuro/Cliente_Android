package com.example.myapplication.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.data.model.ItemModel
import com.example.myapplication.ui.composable.menu.CategoryBar
import com.example.myapplication.ui.composable.menu.ItemDetailBottomSheet
import com.example.myapplication.ui.composable.menu.UniversalItemCard
import com.example.myapplication.ui.viewmodel.MenuViewModel
import com.example.myapplication.ui.viewmodel.HomeProxyViewModel
import kotlinx.coroutines.launch

@Composable
fun MenuScreen() {
    val viewModel: MenuViewModel = hiltViewModel()
    val proxyViewModel: HomeProxyViewModel = hiltViewModel()
    val state by viewModel.state
    val data = state.data

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedCategory by remember { mutableIntStateOf(0) }

    var sectionIndices by remember { mutableStateOf(mapOf<String, Int>()) }

    LaunchedEffect(data) {
        val indices = mutableMapOf<String, Int>()
        var index = 0

        if (data.entrantes.isNotEmpty()) {
            indices["Entrantes"] = index
            index += 3
        }

        if (data.principales.isNotEmpty()) {
            indices["Platos"] = index
            index += 3
        }

        if (data.bebidas.isNotEmpty()) {
            indices["Bebidas"] = index
            index += 3
        }

        if (data.menus.isNotEmpty()) {
            indices["Menús"] = index
            index += 3
        }

        if (data.promociones.isNotEmpty()) {
            indices["Promociones"] = index
            index += 3
        }

        if (data.postres.isNotEmpty()) {
            indices["Postres"] = index
        }

        sectionIndices = indices
    }

    var selectedItem by remember { mutableStateOf<ItemModel?>(null) }

    Column(Modifier.fillMaxSize()) {
        CategoryBar(
            selectedCategory = selectedCategory,
            onCategorySelected = { index ->
                selectedCategory = index
                val target = when (index) {
                    0 -> sectionIndices["Entrantes"]
                    1 -> sectionIndices["Platos"]
                    2 -> sectionIndices["Bebidas"]
                    3 -> sectionIndices["Menús"]
                    4 -> sectionIndices["Promociones"]
                    5 -> sectionIndices["Postres"]
                    else -> null
                } ?: 0
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(target.coerceAtLeast(0))
                }
            }
        )

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
            }
        } else {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                if (data.entrantes.isNotEmpty()) {
                    item { SectionTitle("Entrantes") }
                    item { MenuItemGrid(items = data.entrantes, onItemClick = { selectedItem = it }) }
                    item { Spacer(Modifier.height(16.dp)) }
                }

                if (data.principales.isNotEmpty()) {
                    item { SectionTitle("Platos") }
                    item { MenuItemGrid(items = data.principales, onItemClick = { selectedItem = it }) }
                    item { Spacer(Modifier.height(16.dp)) }
                }

                if (data.bebidas.isNotEmpty()) {
                    item { SectionTitle("Bebidas") }
                    item { MenuItemGrid(items = data.bebidas, onItemClick = { selectedItem = it }) }
                    item { Spacer(Modifier.height(16.dp)) }
                }

                if (data.menus.isNotEmpty()) {
                    item { SectionTitle("Menús") }
                    item { MenuItemGrid(items = data.menus, onItemClick = { selectedItem = it }) }
                    item { Spacer(Modifier.height(16.dp)) }
                }

                if (data.promociones.isNotEmpty()) {
                    item { SectionTitle("Promociones") }
                    item { MenuItemGrid(items = data.promociones, onItemClick = { selectedItem = it }) }
                    item { Spacer(Modifier.height(16.dp)) }
                }

                if (data.postres.isNotEmpty()) {
                    item { SectionTitle("Postres") }
                    item { MenuItemGrid(items = data.postres, onItemClick = { selectedItem = it }) }
                    item { Spacer(Modifier.height(32.dp)) }
                }
            }
        }
    }

    selectedItem?.let { item ->
        ItemDetailBottomSheet(
            item = item,
            cartViewModel = proxyViewModel.cartViewModel,
            onDismiss = { selectedItem = null }
        )
    }
}

@Composable
private fun MenuItemGrid(
    items: List<ItemModel>,
    onItemClick: (ItemModel) -> Unit
) {
    Column {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        UniversalItemCard(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
                if (rowItems.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}