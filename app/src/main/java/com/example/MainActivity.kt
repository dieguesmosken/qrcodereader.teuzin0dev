package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.preferences.AppPreferences
import com.example.data.repository.ScanRepository
import com.example.ui.navigation.AppNavigation
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppViewModel
import com.example.ui.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "scan_history.db"
        ).build()

        val repository = ScanRepository(database.scanHistoryDao())
        val preferences = AppPreferences(applicationContext)

        val factory = AppViewModelFactory(repository, preferences)
        val viewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        setContent {
            val isDarkModeConfig by viewModel.isDarkMode.collectAsState()
            
            MyApplicationTheme(darkTheme = isDarkModeConfig) {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
