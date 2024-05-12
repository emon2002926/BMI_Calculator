package com.example.bmicalculator.viewModel

import androidx.lifecycle.ViewModel
import com.example.bmicalculator.R
import com.example.bmicalculator.models.Food
import com.example.bmicalculator.util.FoodList

class BmiResultViewModel :ViewModel(){

    private var _bmiState:String=""
    private var _backgroundColor:Int=R.color.red

    val genderOptions = arrayOf("Male", "Female", "Other")
    val heightFormats = arrayOf("ft", "cm")
    val weightFormats = arrayOf("kg", "lb")

    fun getBmiScore(heightFormat:String,weightFormat:String,height:String,weight:String):Double{
        val userHeight = if (heightFormat == "cm") height.toDouble() * 0.01 else height.toDouble() * 0.3048
        val userWeight = if (weightFormat == "lb") weight.toDouble() * 0.45359237 else weight.toDouble()
        return userWeight / (userHeight * userHeight)
    }

    private var list =  emptyList<Food>()

    fun calculateBMIState(score: Double){

        when{

        score < 18.5 -> {
            _bmiState = "UnderWeight"
            _backgroundColor= R.color.blue
            list = FoodList.underWeight
        }
        score in 18.5..24.9 -> {
            _bmiState = "Normal"
            _backgroundColor= R.color.green
            list =FoodList.normal

        }
        score in 25.0..30.0 -> {
            _bmiState = "OverWeight"
            _backgroundColor = R.color.darkOrange
            list =FoodList.overWeight

        }
        else -> {
            _bmiState = "Obese"
            _backgroundColor = R.color.red
            list =FoodList.obes

        }
    }
    }

    fun getFoodList():List<Food>{
        return list
    }

    fun bmiState():String= _bmiState
    fun backgroundColor():Int=_backgroundColor
}