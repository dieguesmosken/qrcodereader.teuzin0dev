package com.example.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_history")
data class ScanHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val value: String,
    val format: String = "QR_CODE",
    val timestamp: Long = System.currentTimeMillis()
)
