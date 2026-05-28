package com.example.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import com.example.ui.history.HistoryScreen
import com.example.ui.login.LoginScreen
import com.example.ui.scanner.ScannerScreen
import com.example.ui.settings.SettingsScreen
import com.example.ui.about.AboutScreen
import com.example.ui.viewmodel.AppViewModel

sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Login : Screen("login", "Login", {})
    object Scanner : Screen("scanner", "Scanner", { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scanner") })
    object Create : Screen("create", "Criar", { Icon(Icons.Default.Add, contentDescription = "Criar") })
    object History : Screen("history", "Histórico", { Icon(Icons.Default.History, contentDescription = "Histórico") })
    object Settings : Screen("settings", "Configurações", { Icon(Icons.Default.Settings, contentDescription = "Configurações") })
    object About : Screen("about", "Sobre", {})
}

@Composable
fun AppNavigation(viewModel: AppViewModel) {
    val navController = rememberNavController()
    val userName by viewModel.userName.collectAsState()

    val screens = listOf(
        Screen.Scanner,
        Screen.Create,
        Screen.History,
        Screen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isLoginScreen = currentDestination?.route == Screen.Login.route

    Scaffold(
        bottomBar = {
            if (!isLoginScreen) {
                NavigationBar {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = screen.icon,
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Scanner.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = if (userName == null) Screen.Login.route else Screen.Scanner.route, Modifier.padding(innerPadding)) {
            composable(Screen.Login.route) {
                LoginScreen(
                    viewModel = viewModel,
                    onNavigateNext = {
                        navController.navigate(Screen.Scanner.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Scanner.route) {
                ScannerScreen(
                    viewModel = viewModel,
                    onNavigateToHistory = { navController.navigate(Screen.History.route) },
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
                )
            }
            composable(Screen.Create.route) {
                com.example.ui.create.CreateScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.History.route) {
                HistoryScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onLogout = { 
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onNavigateToAbout = { navController.navigate(Screen.About.route) }
                )
            }
            composable(Screen.About.route) {
                AboutScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
