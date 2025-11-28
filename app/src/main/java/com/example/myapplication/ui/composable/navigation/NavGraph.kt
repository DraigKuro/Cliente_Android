package com.example.myapplication.ui.composable.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.composable.screen.HomeScreen
import com.example.myapplication.ui.composable.screen.menu.MenuScreen
import com.example.myapplication.ui.composable.screen.CartScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Menu.route) { MenuScreen() }
        composable(BottomNavItem.Cart.route) { CartScreen() }
    }
}