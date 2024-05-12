package com.example.bmicalculator

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bmicalculator.adapter.FoodApter
import com.example.bmicalculator.databinding.ActivityBmiResultBinding
import com.example.bmicalculator.models.Food
import com.example.bmicalculator.models.UserData
import com.example.bmicalculator.util.FoodList
import com.example.bmicalculator.viewModel.BmiResultViewModel
import java.math.RoundingMode


class BmiResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityBmiResultBinding

    private val viewModel:BmiResultViewModel by  viewModels()

    private var list =  emptyList<Food>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUi()


        val adapter = FoodApter(calculateBMIState(22.00))




    }





    fun calculateBMIState(score: Double):List<Food>{
         when{
            score < 18.5 -> {
                list = FoodList.underWeight
            }
            score in 18.5..24.9 -> {
                list = FoodList.normal
            }
            score in 25.0..30.0 -> {
                list = FoodList.overWeight
            }
            else -> {

                list = FoodList.obes

            }

        }
        return list
    }


    /*
    fun aboutBmi() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.about_bmi)

        val recyclerView = dialog.findViewById<RecyclerView>(R.id.recView2)
        recyclerView.layoutManager = LinearLayoutManager(this@BmiResultActivity)

        val adapter = FoodApter(FoodList.foodList)
        recyclerView.adapter = adapter

        val btnClose = dialog.findViewById<AppCompatButton>(R.id.buttonClose)
        btnClose.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

     */
    /*
    fun calculateBMIState(score: Double):List<Food>{
        val foodList = FoodList

        return when{
            score < 18.5 -> {

                foodList.underWeight
            }
            score in 18.5..24.9 -> {

                foodList.normal
            }
            score in 25.0..30.0 -> {

                foodList.overWeight
            }
            else -> {

                foodList.obes
            }
        }
    }
     */




    private fun updateUi(){
        val userData = intent.getParcelableExtra<UserData>("USER_INFO")

        userData?.bmiScore?.let { viewModel.calculateBMIState(it) }


        with(binding){
            bmiConditionTextView.text=viewModel.bmiState()
            viewBackground.setBackgroundColor(ContextCompat.getColor(this@BmiResultActivity, viewModel.backgroundColor()))
            showHeightTextView.text=userData?.height
            showWeightTextView.text=userData?.weight
            showGenderTextView.text=userData?.gender
            showAgeTextView.text=userData?.age
            showBmiTextView.text = "${userData?.bmiScore?.toBigDecimal()
                ?.setScale(1, RoundingMode.CEILING)}"

            aboutBmi.setOnClickListener{ showInstructionsDialog() }
            showDietPlan.setOnClickListener {
                startActivity(Intent(this@BmiResultActivity,FoodActivity::class.java))
            }
            calculateMoreBtn.setOnClickListener { onBackPressed() }
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