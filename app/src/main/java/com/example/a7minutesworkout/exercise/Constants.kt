package com.example.a7minutesworkout.exercise

import com.example.a7minutesworkout.R

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        exerciseList.addAll(
            listOf(
                ExerciseModel(
                    id = 1,
                    name = "Jumping Jacks",
                    image = R.drawable.ic_jumping_jacks
                ),
                ExerciseModel(
                    id = 2,
                    "Wall Sit",
                    image = R.drawable.ic_wall_sit,
                ),
                ExerciseModel(
                    id = 3,
                    "Push Up",
                    image = R.drawable.ic_push_up,
                ),
                ExerciseModel(
                    id = 4,
                    "Abdominal Crunch",
                    image = R.drawable.ic_abdominal_crunch,
                ),
                ExerciseModel(
                    id = 5,
                    "Step-Up onto chair",
                    image = R.drawable.ic_step_up_onto_chair,
                ),
                ExerciseModel(
                    id = 6,
                    "Squat",
                    image = R.drawable.ic_squat,
                ),
                ExerciseModel(
                    id = 7,
                    "Tricep",
                    image = R.drawable.ic_triceps_dip_on_chair,
                ),
                ExerciseModel(
                    id = 8,
                    "Plank",
                    image = R.drawable.ic_plank,
                ),
                ExerciseModel(
                    id = 9,
                    "High Knees Running in Place",
                    image = R.drawable.ic_high_knees_running_in_place,
                ),
                ExerciseModel(
                    id = 10,
                    "Lunge",
                    image = R.drawable.ic_lunge,
                ),
                ExerciseModel(
                    id = 11,
                    "Push up and Rotation",
                    image = R.drawable.ic_push_up_and_rotation,
                ),
                ExerciseModel(
                    id = 12,
                    "Side Plank",
                    image = R.drawable.ic_side_plank,
                ),
            )
        )
        return exerciseList
    }
}