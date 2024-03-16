package com.example.a7minutesworkout.data.history

import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun insert(): Unit

    fun getAllDates(): Flow<List<HistoryEntity>>
}