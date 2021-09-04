package com.suprit.hireaudit.dao

import androidx.room.*
import com.suprit.hireaudit.entities.Users

@Dao
interface UserDao {
    @Insert
    suspend fun registerUser(user : Users)

    @Query("Select * from Users where username=(:username) and userPassword=(:password)")
    suspend fun findUsers(username : String, password : String) : Users

    @Update
    suspend fun updateUserData(user: Users)

    @Delete
    suspend fun deleteUser(user: Users)
}