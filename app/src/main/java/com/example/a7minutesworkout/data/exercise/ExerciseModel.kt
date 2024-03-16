package com.example.a7minutesworkout.data.exercise

data class ExerciseModel (
    var id: Int,
    var name: String,
    var image: Int,
    var isCompleted: Boolean = false,
    var isSelected: Boolean = false
)