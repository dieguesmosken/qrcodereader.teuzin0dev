package com.example.data.repository

import com.example.data.local.ScanHistoryDao
import com.example.domain.models.ScanHistory
import kotlinx.coroutines.flow.Flow

class ScanRepository(private val dao: ScanHistoryDao) {
    val allHistory: Flow<List<ScanHistory>> = dao.getAllHistory()

    suspend fun insert(scanHistory: ScanHistory) = dao.insert(scanHistory)

    suspend fun deleteById(id: Long) = dao.deleteById(id)

    suspend fun clearHistory() = dao.clearHistory()
}
