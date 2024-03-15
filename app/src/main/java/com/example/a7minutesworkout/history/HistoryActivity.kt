package com.example.a7minutesworkout.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.WorkOutApp
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setUpView()
        setUpActionBar()

        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)
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

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { completedDatesList ->
                binding?.apply {
                    if (completedDatesList.isNotEmpty()) {
                        tvHistory.visibility = View.VISIBLE
                        rvHistory.visibility = View.VISIBLE
                        tvNoDataAvailable.visibility = View.INVISIBLE

                        val dates = ArrayList<String>()
                        completedDatesList.forEach { history ->
                            dates.add(history.date)
                        }

                        rvHistory.apply {
                            layoutManager = LinearLayoutManager(this@HistoryActivity)
                            adapter = HistoryAdapter(items = dates)
                        }
                    } else {
                        tvHistory.visibility = View.GONE
                        rvHistory.visibility = View.GONE
                        tvNoDataAvailable.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
