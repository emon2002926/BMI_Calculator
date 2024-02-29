package com.example.bmicalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.bmicalculator.databinding.ActivityMainBinding
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGenderDropDown()
        setupHeightDropDown()
        setupWeightDropDown()

        binding.calculateBmiBtn.setOnClickListener { getUserInfo() }
    }

    private fun setupGenderDropDown() {
        val genderOptions = arrayOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(this, R.layout.gender_list_item, genderOptions)
        binding.autoCompleteGender.setAdapter(adapter)
    }

    private fun setupHeightDropDown() {
        val heightFormats = arrayOf("ft", "cm")
        val adapter = ArrayAdapter(this, R.layout.gender_list_item, heightFormats)
        binding.autoCompleteHeightFormat.setAdapter(adapter)

        binding.autoCompleteHeightFormat.setOnItemClickListener { _, _, position, _ ->
            val format = adapter.getItem(position)
            binding.editTextHeight.hint = if (format == "ft") "5.7$format" else "173$format"
        }
    }

    private fun setupWeightDropDown() {
        val weightFormats = arrayOf("kg", "lb")
        val adapter = ArrayAdapter(this, R.layout.gender_list_item, weightFormats)
        binding.autoCompleteWeightFormat.setAdapter(adapter)

        binding.autoCompleteWeightFormat.setOnItemClickListener { _, _, position, _ ->
            val format = adapter.getItem(position)
            binding.editTextWeight.hint = if (format == "kg") "82$format" else "180$format"
        }
    }

    private fun getUserInfo() {
        val height = binding.editTextHeight.text.toString().trim()
        val weight = binding.editTextWeight.text.toString().trim()
        val age = binding.editTextAge.text.toString().trim()
        val gender = binding.autoCompleteGender.text.toString()
        val heightFormat = binding.autoCompleteHeightFormat.text.toString()
        val weightFormat = binding.autoCompleteWeightFormat.text.toString()


        if (height.isEmpty()){
            binding.editTextHeight.error = "height is Required"
            binding.editTextHeight.requestFocus()

            return
        }
        else if (weight.isEmpty()){
            binding.editTextWeight.let {
                it.error= "Full name is Required"
                it.requestFocus()
            }
            return
        } else if (age.isEmpty()){
            binding.editTextAge.let {
                it.error = "Please enter a Valid Id"
                it.requestFocus()
            }
            return
        }
        else if (gender.isEmpty()){
            binding.autoCompleteGender.let {
                it.error = "Please pleace select a gender"
                it.requestFocus()
            }
            return
        }

        val userHeight = if (heightFormat == "cm") height.toDouble() * 0.01 else height.toDouble() * 0.3048
        val userWeight = if (weightFormat == "lb") weight.toDouble() * 0.45359237 else weight.toDouble()

        val bmiScore = calculateBmi(userHeight, userWeight)

        val intent = Intent(this@MainActivity, BmiResultActivity::class.java).apply {
            putExtra("height", "$height$heightFormat")
            putExtra("weight", "$weight$weightFormat")
            putExtra("gender", gender)
            putExtra("age", age)
            putExtra("bmiScore", bmiScore)
        }
        startActivity(intent)

        // Clear input fields and reset dropdown menus
        with(binding){
            editTextHeight.text.clear()
            editTextWeight.text.clear()
            editTextAge.text.clear()
            autoCompleteGender.setText("")
            autoCompleteHeightFormat.setText("")
            autoCompleteWeightFormat.setText("")
        }
    }
    private fun calculateBmi(height: Double, weight: Double): Double {
        return weight / (height * height)
    }

}