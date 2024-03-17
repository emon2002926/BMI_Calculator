package com.example.bmicalculator

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.databinding.ActivityMainBinding
import com.example.bmicalculator.models.UserData
import com.example.bmicalculator.viewModel.BmiResultViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: BmiResultViewModel by  viewModels()

    private val dropDownLayout = R.layout.item_list

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
        val adapter = ArrayAdapter(this, dropDownLayout, viewModel.genderOptions)
        binding.autoCompleteGender.setAdapter(adapter)
    }

    private fun setupHeightDropDown() {
        val adapter = ArrayAdapter(this, dropDownLayout,viewModel.heightFormats)
        with(binding){
            autoCompleteHeightFormat.setAdapter(adapter)
            autoCompleteHeightFormat.setOnItemClickListener { _, _, position, _ ->
                val format = adapter.getItem(position)
                binding.editTextHeight.hint = if (format == "ft") "5.7$format" else "173$format"
            }
        }

    }

    private fun setupWeightDropDown() {
        val adapter = ArrayAdapter(this,dropDownLayout, viewModel.weightFormats)
        with(binding){
            autoCompleteWeightFormat.setAdapter(adapter)
            autoCompleteWeightFormat.setOnItemClickListener { _, _, position, _ ->
                val format = adapter.getItem(position)
                binding.editTextWeight.hint = if (format == "kg") "82$format" else "180$format"
            }
        }

    }

    private fun getUserInfo() {

        with(binding){
            val height = editTextHeight.text.toString().trim()
            val weight = editTextWeight.text.toString().trim()
            val age = editTextAge.text.toString().trim()
            val gender = autoCompleteGender.text.toString()
            val heightFormat = autoCompleteHeightFormat.text.toString()
            val weightFormat = autoCompleteWeightFormat.text.toString()

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


            val bmiScore = viewModel.getBmiScore(heightFormat,weightFormat,height, weight)
            val intent = Intent(this@MainActivity, BmiResultActivity::class.java).apply {
                val userData = UserData(
                    "$height$heightFormat",
                    "$weight$weightFormat",
                    gender,
                    age,
                    bmiScore
                )
                putExtra("USER_INFO",userData)

            }

            startActivity(intent)

            editTextHeight.text.clear()
            editTextWeight.text.clear()
            editTextAge.text.clear()
            autoCompleteGender.setText("")
            autoCompleteHeightFormat.setText("")
            autoCompleteWeightFormat.setText("")

        }
    }


}