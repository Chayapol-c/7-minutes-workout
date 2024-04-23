package com.example.a7minutesworkout.ui.finish


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null
    private val viewModel: FinishViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)

        binding?.apply {
            setContentView(root)
            setSupportActionBar(toolbarFinishActivity)
            toolbarFinishActivity.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            btnFinish.setOnClickListener {
                finish()
            }
        }

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        viewModel.addDateToDatabase()
    }
}