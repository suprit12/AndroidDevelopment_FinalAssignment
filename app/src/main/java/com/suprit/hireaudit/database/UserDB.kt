package com.suprit.hireaudit.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.suprit.hireaudit.dao.UserDao
import com.suprit.hireaudit.entities.Users
import androidx.room.Room
import com.suprit.hireaudit.dao.AccountantDao
import com.suprit.hireaudit.entities.Accountant

@Database(
    entities = [(Users::class), (Accountant::class)],
    version = 1,
    exportSchema = false
)
abstract class UserDB : RoomDatabase(){
    abstract fun getUserDao() : UserDao
    abstract fun getAccountantDao() : AccountantDao

    companion object{
        private var instance : UserDB? = null

        fun getInstance(context: Context) : UserDB {
            if (instance == null){
                synchronized(Users::class){
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, UserDB::class.java, "HireAccountants"
        ).addMigrations().build()
    }
}