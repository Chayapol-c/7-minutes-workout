package com.example.a7minutesworkout.exercise

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusViewHolder(private val binding: ItemExerciseStatusBinding): RecyclerView.ViewHolder(binding.root) {

    fun setViewData(item: ExerciseModel) {
        binding.apply {
            tvItem.text = item.id.toString()

            when {
                item.isSelected -> {
                    tvItem.background = ContextCompat.getDrawable(tvItem.context, R.drawable.item_circular_thin_accent_border)
                    tvItem.setTextColor(Color.parseColor("#212121"))
                }
                item.isCompleted -> {
                    tvItem.background = ContextCompat.getDrawable(tvItem.context, R.drawable.item_circular_color_accent_background)
                    tvItem.setTextColor(Color.parseColor("#FFFFFF"))
                }
                else -> {
                    tvItem.background = ContextCompat.getDrawable(tvItem.context, R.drawable.item_circular_gray_background)
                    tvItem.setTextColor(Color.parseColor("#212121"))
                }
            }
        }
    }

}