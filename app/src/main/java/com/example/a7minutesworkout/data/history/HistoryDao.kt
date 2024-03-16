package com.example.a7minutesworkout.data.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.a7minutesworkout.data.history.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Query(value = "SELECT * FROM `history-table`")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}