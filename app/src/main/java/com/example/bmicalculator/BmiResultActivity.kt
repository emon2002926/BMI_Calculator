package com.example.bmicalculator

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.bmicalculator.databinding.ActivityBmiResultBinding
import java.math.RoundingMode

class BmiResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityBmiResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val height =intent.getStringExtra("height")
        val weight =intent.getStringExtra("weight")
        val gender =intent.getStringExtra("gender")
        val age =intent.getStringExtra("age")
        val bmiScore =(intent.getDoubleExtra("bmiScore",0.0))
        updateUi(height,weight,gender,age,bmiScore)


        var bmiState =""
        var bangroundColor =0

        when {
            bmiScore < 18.5 -> {
                bmiState = "UnderWeight"
                bangroundColor= R.color.blue
            }
            bmiScore in 18.5..24.9 -> {
                bmiState = "Normal"
                bangroundColor= R.color.green
            }
            bmiScore in 25.0..30.0 -> {
                bmiState = "OverWeight"
                bangroundColor = R.color.darkOrange
            }
            else -> {
                bmiState = "Obese"
                bangroundColor = R.color.red
            }
        }

        with(binding){
            aboutBmi.setOnClickListener{ showInstructionsDialog() }
            bmiConditionTextView.text=bmiState
            viewBackground.setBackgroundColor(ContextCompat.getColor(this@BmiResultActivity, bangroundColor))
            calculateMoreBtn.setOnClickListener { onBackPressed() }
        }
    }

    private fun updateUi(height:String?, weight:String?, gender:String?, age:String?, bmiScore:Double?){
        with(binding){
            showHeightTextView.text=height
            showWeightTextView.text=weight
            showGenderTextView.text=gender
            showAgeTextView.text=age
            showBmiTextView.text = "${bmiScore?.toBigDecimal()?.setScale(1,RoundingMode.CEILING)}"
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