package com.suprit.hireaudit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception


class LaunchActivity : AppCompatActivity() {
    private var username : String? = null
    private var password : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            getPreferences()
            verifyUser()
            finish()
        }
    }

    private fun verifyUser(){
        CoroutineScope(Dispatchers.IO).launch {
            if(username == null && password == null){
                withContext(Main){
                    startActivity(Intent(this@LaunchActivity, LoginActivity::class.java))
                    finish()
                }
            }
            else {
                try {


                    val repository = UserRepository()

                    val response = username?.let { password?.let { it1 -> repository.loginUser(it, it1) } }

                    if (response != null) {
                        if (response.success == true) {
                            ServiceBuilder.token = "${response.token}"
                            ServiceBuilder.data = response.data!!

                            startActivity(Intent(this@LaunchActivity, DashboardActivity::class.java))

                            finish()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Main) {
                        startActivity(Intent(this@LaunchActivity, LoginActivity::class.java))
                    }
                }
            }
            }
        }


    private fun getPreferences(){
        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        username = sharedPref!!.getString("username", "").toString()
        password = sharedPref!!.getString("password", "").toString()
    }
}
