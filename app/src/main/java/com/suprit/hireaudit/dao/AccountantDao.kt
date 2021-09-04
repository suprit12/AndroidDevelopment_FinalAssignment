package com.suprit.hireaudit.dao

import androidx.room.*
import com.suprit.hireaudit.entities.Accountant

@Dao
interface AccountantDao {
    @Insert
    suspend fun registerAccountant(accountant : ArrayList<Accountant>)

    @Query("Select * from Accountant")
    suspend fun getAccountants() : MutableList<Accountant>

    @Query("Delete from Accountant")
    suspend fun deleteAccountants()
}