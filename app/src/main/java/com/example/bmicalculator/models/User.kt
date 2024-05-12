package com.example.bmicalculator.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    val height:String,
    val weight:String,
    val gender:String,
    val age:String,
    val bmiScore:Double
)
