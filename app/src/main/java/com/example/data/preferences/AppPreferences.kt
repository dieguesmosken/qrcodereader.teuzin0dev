package com.example.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class AppPreferences(private val context: Context) {
    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val AUTO_OPEN_LINKS_KEY = booleanPreferencesKey("auto_open_links")
        val VIBRATE_ON_SCAN_KEY = booleanPreferencesKey("vibrate_on_scan")
        val USER_NAME_KEY = androidx.datastore.preferences.core.stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = androidx.datastore.preferences.core.stringPreferencesKey("user_email")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    val isAutoOpenLinks: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[AUTO_OPEN_LINKS_KEY] ?: false
    }

    val isVibrateOnScan: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[VIBRATE_ON_SCAN_KEY] ?: true
    }
    
    val userName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY]
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY]
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun setAutoOpenLinks(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_OPEN_LINKS_KEY] = enabled
        }
    }

    suspend fun setVibrateOnScan(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[VIBRATE_ON_SCAN_KEY] = enabled
        }
    }
    
    suspend fun setUser(name: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_EMAIL_KEY] = email
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_NAME_KEY)
            preferences.remove(USER_EMAIL_KEY)
        }
    }
}
