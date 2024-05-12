package com.example.bmicalculator.db

import androidx.room.Dao
import androidx.room.Insert
import com.example.bmicalculator.models.User

@Dao
interface UserDao {
    @Insert
    fun addUser(user: User)
}