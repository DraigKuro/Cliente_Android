package com.example.myapplication.ui.composable.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Inicio", Icons.Filled.Home)
    object Menu : BottomNavItem("menu", "Menú", Icons.Filled.RestaurantMenu)
    object Cart : BottomNavItem("cart", "Carrito", Icons.Filled.ShoppingCart)
}