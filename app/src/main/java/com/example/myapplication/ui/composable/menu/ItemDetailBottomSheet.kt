package com.example.myapplication.ui.composable.menu

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.model.ItemModel
import com.example.myapplication.data.model.PromotionModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailBottomSheet(
    item: ItemModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val maxLimit = if (item is PromotionModel) {
        item.cantidad
    } else {
        99
    }

    var quantity by remember { mutableIntStateOf(1) }
    var maxQuantity by remember { mutableIntStateOf(maxLimit) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            AsyncImage(
                model = BuildConfig.API_BASE_URL + item.imagen,
                contentDescription = item.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = item.nombre,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "${item.precio} €",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = item.descripcion.takeIf { it.isNotBlank() } ?: "Sin descripción disponible",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                QuantitySelector(
                    quantity = quantity,
                    onQuantityChange = {
                        if (it <= maxQuantity) quantity = it
                    }
                )

                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(
                        text = "Añadir al carrito",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            )
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
            enabled = quantity > 1
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Restar",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(
            onClick = { onQuantityChange(quantity + 1) }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Sumar",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}