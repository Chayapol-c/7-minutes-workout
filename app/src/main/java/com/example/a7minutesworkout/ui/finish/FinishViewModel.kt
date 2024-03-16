package com.example.a7minutesworkout.ui.finish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a7minutesworkout.data.history.HistoryDao
import com.example.a7minutesworkout.data.history.HistoryEntity
import com.example.a7minutesworkout.data.history.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FinishViewModel @Inject constructor(
    private val repository: HistoryRepository
) : ViewModel() {

    fun addDateToDatabase() {
        viewModelScope.launch {
            repository.insert()
        }
    }
}