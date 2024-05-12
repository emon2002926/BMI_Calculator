package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmicalculator.adapter.FoodApter
import com.example.bmicalculator.databinding.ActivityFoodBinding
import com.example.bmicalculator.viewModel.BmiResultViewModel

class FoodActivity : AppCompatActivity() {
    lateinit var binding: ActivityFoodBinding
    private val viewModel: BmiResultViewModel by  viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.calculateBMIState(22.00)


        setupRecyclerview()
    }
    private fun setupRecyclerview()=binding.recView.apply{

        val foodApter = FoodApter(viewModel.getFoodList())
        adapter=foodApter
        layoutManager = LinearLayoutManager(this@FoodActivity)

    }
}