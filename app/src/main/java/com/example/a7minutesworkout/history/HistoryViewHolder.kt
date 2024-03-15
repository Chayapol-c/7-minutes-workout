package com.example.a7minutesworkout.history

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryViewHolder(private val binding: ItemHistoryRowBinding): RecyclerView.ViewHolder(binding.root) {

    fun setViewData(item: String, position: Int) {
        binding.apply {
            tvPosition.text = position.toString()
            tvItem.text = item

            if (position % 2 == 0) {
                llHistoryItemMain.setBackgroundColor(ContextCompat.getColor(this.llHistoryItemMain.context, R.color.lightGrey))
            } else {
                llHistoryItemMain.setBackgroundColor(ContextCompat.getColor(this.llHistoryItemMain.context, R.color.white))
            }
        }
    }

}