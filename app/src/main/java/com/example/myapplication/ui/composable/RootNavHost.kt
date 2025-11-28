package com.example.myapplication.ui.composable

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.composable.screen.MainContentWithBottomBar
import com.example.myapplication.ui.composable.screen.QrScanEntryScreen

private const val QR_ROUTE = "qr_scan_entry_route"
private const val MAIN_CONTENT_ROUTE = "main_content_route"

@Composable
fun RootNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = QR_ROUTE
    ) {
        composable(QR_ROUTE) {
            QrScanEntryScreen(
                onTableConnected = {
                    navController.navigate(MAIN_CONTENT_ROUTE) {
                        popUpTo(QR_ROUTE) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(MAIN_CONTENT_ROUTE) {
            MainContentWithBottomBar()
        }
    }
}