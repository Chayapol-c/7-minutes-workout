package com.example.a7minutesworkout.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a7minutesworkout.data.history.HistoryDao
import com.example.a7minutesworkout.data.history.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepository,
) : ViewModel() {
    private val _isShowHistory = MutableLiveData(false)
    val isShowHistory: LiveData<Boolean> = _isShowHistory

    private val _dateList = MutableLiveData<ArrayList<String>>()
    val dateList: LiveData<ArrayList<String>> = _dateList

    fun getAllCompleteDates() {
        viewModelScope.launch {
            repository.getAllDates().collect { completedDatesList ->
                if (completedDatesList.isNotEmpty()) {
                    completedDatesList.forEach { history ->
                        _dateList.value?.add(history.date)
                    }
                    _isShowHistory.value = true
                } else {
                    _isShowHistory.value = false
                }
            }
        }
    }
}