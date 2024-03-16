package com.example.a7minutesworkout.ui.bmi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.RoundingMode
import kotlin.math.pow

class BmiActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setUpView()
        setUpActionBar()
    }

    private fun setUpActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "CALCULATE BMI"
        }
    }

    private fun setUpView() {
        binding?.apply {
            setContentView(root)
            setSupportActionBar(toolbarBmiActivity)
            toolbarBmiActivity.setNavigationOnClickListener {
                onBackPressed()
            }
            btnCalculate.setOnClickListener {
                calculateUnits()
            }
            onDisplayMetricUnits()

            rgUnits.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbMetricUnits -> onDisplayMetricUnits()
                    R.id.rbUsUnits -> onDisplayUsUnits()
                }
            }
        }
    }

    private fun calculateUnits() = when(currentVisibleView) {
        METRIC_UNITS_VIEW -> {
            if (validateMetricUnits()) {
                val height: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weight: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weight / height.pow(2)

                displayBmiResult(bmi)
            } else {
                Toast.makeText(this@BmiActivity, "Please Enter valid Value", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        US_UNITS_VIEW -> {
            if (validateUsUnits()) {
                val weight: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val feet: Float = binding?.etUsUnitHeightFeet?.text.toString().toFloat()
                val inch: Float = binding?.etUsUnitHeightInch?.text.toString().toFloat()

                val height = inch + feet * 12
                val bmi = 703 * (weight / height.pow(2))
                displayBmiResult(bmi)
            } else {
                Toast.makeText(this@BmiActivity, "Please Enter valid Value", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        else -> {

        }
    }

    private fun onDisplayMetricUnits() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.apply {
            tilMetricUnitWeight.visibility = View.VISIBLE
            tilMetricUnitHeight.visibility = View.VISIBLE

            tilUsUnitHeightFeet.visibility = View.INVISIBLE
            tilUsUnitHeightInch.visibility = View.INVISIBLE

            etMetricUnitHeight.text?.clear()
            etMetricUnitWeight.text?.clear()

            llDisplayBmiResult.visibility = View.INVISIBLE
        }
    }

    private fun onDisplayUsUnits() {
        currentVisibleView = US_UNITS_VIEW
        binding?.apply {
            tilMetricUnitWeight.visibility = View.VISIBLE
            tilMetricUnitHeight.visibility = View.INVISIBLE

            tilUsUnitHeightFeet.visibility = View.VISIBLE
            tilUsUnitHeightInch.visibility = View.VISIBLE

            etUsUnitHeightFeet.text?.clear()
            etUsUnitHeightInch.text?.clear()
            etMetricUnitWeight.text?.clear()

            llDisplayBmiResult.visibility = View.INVISIBLE
        }
    }

    private fun displayBmiResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Oops! You really need to take better care of yourself"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Moderately Obese"
            bmiDescription = "Oops! You really need to take better care of yourself"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Severely obese"
            bmiDescription = "Oops! You really need to take better care of yourself"
        } else {
            bmiLabel = "Very Severely obese"
            bmiDescription = "Oops! You really need to take better care of yourself"
        }
        val bmiValue = bmi.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.apply {
            llDisplayBmiResult.visibility = View.VISIBLE
            tvBmiValue.text = bmiValue
            tvBmiType.text = bmiLabel
            tvBmiDescription.text = bmiDescription
        }
    }

    private fun validateMetricUnits() = binding?.etMetricUnitWeight?.text.toString()
        .isNotEmpty() && binding?.etMetricUnitHeight?.text.toString().isNotEmpty()

    private fun validateUsUnits() = binding?.etUsUnitHeightFeet?.text.toString()
        .isNotEmpty() && binding?.etUsUnitHeightInch?.text.toString()
        .isNotEmpty() && binding?.etMetricUnitWeight?.text.toString().isNotEmpty()

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }
}