package com.example.a7minutesworkout.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val items: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseStatusViewHolder {
        return ExerciseStatusViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseStatusViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.setViewData(model)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}