package com.example.bmicalculator.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserData(
    val height:String,
    val weight:String,
    val gender:String,
    val age:String,
    val bmiScore:Double
    ): Parcelable
