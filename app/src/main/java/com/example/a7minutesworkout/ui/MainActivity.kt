package com.example.a7minutesworkout.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.ui.bmi.BmiActivity
import com.example.a7minutesworkout.databinding.ActivityMainBinding
import com.example.a7minutesworkout.ui.exercise.ExerciseActivity
import com.example.a7minutesworkout.ui.history.HistoryActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            flStart.setOnClickListener {
                val intent = Intent(this@MainActivity, ExerciseActivity::class.java)
                startActivity(intent)
            }

            flBmi.setOnClickListener {
                val intent = Intent(this@MainActivity, BmiActivity::class.java)
                startActivity(intent)
            }

            flHistory.setOnClickListener {
                val intent = Intent(this@MainActivity, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }
}