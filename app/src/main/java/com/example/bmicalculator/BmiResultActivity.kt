package com.example.bmicalculator

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmicalculator.adapter.FoodApter
import com.example.bmicalculator.databinding.ActivityBmiResultBinding
import com.example.bmicalculator.models.UserData
import com.example.bmicalculator.util.FoodList
import com.example.bmicalculator.viewModel.BmiResultViewModel
import java.math.RoundingMode


class BmiResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityBmiResultBinding

    private val viewModel:BmiResultViewModel by  viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUi()

        setupRecyclerview()


    }
    private fun setupRecyclerview()=binding.recView.apply{
        val foodApter = FoodApter(FoodList.foodList)
        adapter=foodApter
        layoutManager = LinearLayoutManager(this@BmiResultActivity)

    }

    private fun updateUi(){
        val userData = intent.getParcelableExtra<UserData>("USER_INFO")

        userData?.bmiScore?.let { viewModel.calculateBMIState(it) }

        with(binding){
            aboutBmi.setOnClickListener{ showInstructionsDialog() }
            bmiConditionTextView.text=viewModel.bmiState()
            viewBackground.setBackgroundColor(ContextCompat.getColor(this@BmiResultActivity, viewModel.backgroundColor()))
            calculateMoreBtn.setOnClickListener { onBackPressed() }

            showHeightTextView.text=userData?.height
            showWeightTextView.text=userData?.weight
            showGenderTextView.text=userData?.gender
            showAgeTextView.text=userData?.age
            showBmiTextView.text = "${userData?.bmiScore?.toBigDecimal()
                ?.setScale(1, RoundingMode.CEILING)}"
        }
    }

    private fun showInstructionsDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.about_bmi)
        val buttonClose = dialog.findViewById<Button>(R.id.buttonClose)
        buttonClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }




}