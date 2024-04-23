package com.example.a7minutesworkout.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a7minutesworkout.CountDownTimerLiveData
import com.example.a7minutesworkout.data.exercise.Constants
import com.example.a7minutesworkout.data.exercise.ExerciseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor() : ViewModel() {

    private val _restTimer = CountDownTimerLiveData(REST_DURATION, 1000)
    val restTimer: LiveData<Long> = _restTimer

    private val _exerciseTimer = CountDownTimerLiveData(EXERCISE_DURATION, 1000)
    val exerciseTimer: LiveData<Long> = _exerciseTimer

    private var _exercisePosition = MutableLiveData(0)
//    val exercisePosition: LiveData<Int> = _exercisePosition

    private var _exerciseList = MutableLiveData<ArrayList<ExerciseModel>>()
    val exerciseList = _exerciseList

    init {
        _exerciseList.value = Constants.defaultExerciseList()
    }

    fun startRestTimer() {
        _restTimer.start()
    }

    fun resetRestTimer() {
        _restTimer.reset()
    }

    fun startExerciseTimer() {
        _exerciseTimer.start()
    }

    fun resetExerciseTimer() {
        _exerciseTimer.stop()
    }


    fun getCurrentExercise(): ExerciseModel? {
        val excercise = _exercisePosition.value?.let { pos ->
            _exerciseList.value?.get(pos)
        }
        return excercise
    }

    fun updateCurrentExercise() {
        _exercisePosition.value?.let { pos ->
            _exerciseList.value?.get(pos)?.isSelected = true
        }
    }

    fun doneExercise() {
        _exercisePosition.value = _exercisePosition.value?.plus(1)
        _exercisePosition.value?.let { pos ->
            _exerciseList.value?.get(pos)?.isSelected = false
            _exerciseList.value?.get(pos)?.isSelected = true
        }
    }

    fun isDoneAllExercises(): Boolean {
        return _exercisePosition.value!! < _exerciseList.value!!.size - 1
    }

    companion object {
        const val EXERCISE_DURATION = 10L
        const val REST_DURATION = 3L
    }
}