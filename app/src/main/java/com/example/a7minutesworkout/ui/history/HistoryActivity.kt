package com.example.a7minutesworkout.ui.history

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setUpView()
        setUpActionBar()

        viewModel.isShowHistory.observe(this) {
            if (it) {
                binding?.apply {
                    tvHistory.visibility = View.VISIBLE
                    rvHistory.visibility = View.VISIBLE
                    tvNoDataAvailable.visibility = View.INVISIBLE


                }
            } else {
                binding?.apply {
                    tvHistory.visibility = View.GONE
                    rvHistory.visibility = View.GONE
                    tvNoDataAvailable.visibility = View.VISIBLE
                }
            }
        }

        viewModel.dateList.observe(this) {
            binding?.rvHistory?.apply {
                layoutManager = LinearLayoutManager(this@HistoryActivity)
                adapter = HistoryAdapter(items = it)
            }
        }

        viewModel.getAllCompleteDates()
    }

    private fun setUpActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "HISTORY"
        }
    }

    private fun setUpView() {
        binding?.apply {
            setContentView(root)
            setSupportActionBar(toolbarHistoryActivity)
            toolbarHistoryActivity.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
