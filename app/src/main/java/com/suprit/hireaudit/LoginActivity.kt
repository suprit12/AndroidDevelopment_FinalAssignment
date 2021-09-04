package com.suprit.hireaudit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.suprit.hireaudit.api.ServiceBuilder

import com.suprit.hireaudit.repository.UserRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var loginLayout: LinearLayout

    private lateinit var btnLogin: Button
    private lateinit var tvSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginLayout = findViewById(R.id.loginLayout)
        etUsername = loginLayout.findViewById(R.id.etUsername)
        etPassword = loginLayout.findViewById(R.id.etPassword)

        btnLogin = findViewById(R.id.btnLogin)
        tvSignUp = findViewById(R.id.tvSignUp)

        btnLogin.setOnClickListener {
            login()
        }

        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        saveState(_savedInstanceState = savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)


        outState.putString("etUsername", etUsername.text.toString())
        outState.putString("etPassword", etPassword.text.toString())
    }

    private fun saveState(_savedInstanceState: Bundle?) {
        etUsername.setText(_savedInstanceState?.getString("etUsername"))
        etPassword.setText(_savedInstanceState?.getString("etPassword"))
    }

    private fun login() {
//

        CoroutineScope(Dispatchers.IO).launch {

            val email = etUsername.text.toString()
            val password = etPassword.text.toString()



            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = UserRepository()

                    val response = repository.loginUser(email, password)

                    if (response.success == true) {
                        ServiceBuilder.token = "${response.token}"

                        savePreferences()
                    }
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    finish()

                } catch (io: IOException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@LoginActivity,
                                "Please enter correct credentials",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }


    private fun savePreferences() {
        val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)

        val editor = sharedPref.edit()

        editor.putString("username", etUsername.text.toString())
        editor.putString("password", etPassword.text.toString())

        editor.apply()
    }
}