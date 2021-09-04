package com.suprit.hireaudit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.suprit.hireaudit.LoginActivity
import com.suprit.hireaudit.R
import com.suprit.hireaudit.entities.User
import com.suprit.hireaudit.repository.UserRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.net.ssl.SSLException


class UserSignUpFragment : Fragment() {


    private lateinit var userFormLayout : LinearLayout
    private lateinit var etFName : EditText
    private lateinit var etLName : EditText
    private lateinit var etEmail : EditText
    private lateinit var etUsername : EditText
    private lateinit var radGender : RadioGroup
    private lateinit var radMale : RadioButton
    private lateinit var radFemale : RadioButton
    private lateinit var radOthers : RadioButton
    private lateinit var etPassword : EditText
    private lateinit var etPassword2 : EditText
    private lateinit var btnSignUp : Button
    private lateinit var tvLogin : TextView
    private lateinit var etDOB : EditText
    private lateinit var etMobile : EditText
    private lateinit var etOrganization : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_sign_up, container, false)

        userFormLayout = view.findViewById(R.id.userFormLayout)
        etFName = userFormLayout.findViewById(R.id.etFName)
        etLName = userFormLayout.findViewById(R.id.etLName)
        etEmail = userFormLayout.findViewById(R.id.etEmail)
        etUsername = userFormLayout.findViewById(R.id.etUsername)
        radGender = userFormLayout.findViewById(R.id.radGender)
        radMale = userFormLayout.findViewById(R.id.radMale)
        radFemale = userFormLayout.findViewById(R.id.radFemale)
        radOthers = userFormLayout.findViewById(R.id.radOthers)
        etPassword = userFormLayout.findViewById(R.id.etPassword)
        etPassword2 = userFormLayout.findViewById(R.id.etPassword2)
        btnSignUp = userFormLayout.findViewById(R.id.btnSignUp)
        tvLogin = userFormLayout.findViewById(R.id.tvLogin)
        etDOB = userFormLayout.findViewById(R.id.etDOB)

        etMobile = userFormLayout.findViewById(R.id.etMobNo)
        etOrganization = userFormLayout.findViewById(R.id.etOrganization)


        btnSignUp.setOnClickListener{
            register()
        }

        saveState(_savedInstanceState = savedInstanceState)

        return view
    }

    private fun register(){
        var gender = "Male"

        radGender.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
            if (radMale.isChecked){
                gender = "Male"
            }

            if (radFemale.isChecked){
                gender = "Female"
            }

            if (radOthers.isChecked){
                gender = "Others"
            }
        }


        if(etPassword.text.toString() != etPassword2.text.toString())
        {
            etPassword.setText("")
            etPassword2.setText("")
            etPassword.requestFocus()
            Toast.makeText(context, "Please enter the same passwords on both fields", Toast.LENGTH_SHORT).show()
            return
        }
        else{
            val u1 = User(fName = etFName.text.toString(), lName = etLName.text.toString(), emailAddress = etEmail.text.toString(),
            address = etUsername.text.toString(), gender = gender, password = etPassword.text.toString(), organization = etOrganization
                    .text.toString(), dob = etDOB.text.toString(), mobileNo = etMobile.text.toString())


            CoroutineScope(Dispatchers.IO).launch {

                        try {
                            val userRepository = UserRepository()
                            val response = userRepository.registerUser(u1)

                            if(response.success == true){
                                withContext(Dispatchers.Main){
                                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                                    emptyFields()
                                    startActivity(Intent(context, LoginActivity::class.java))
                                }
                            }
                        }

                        catch (ssl : SSLException){
                            withContext(Dispatchers.Main){
                                Toast.makeText(context, ssl.localizedMessage, Toast.LENGTH_SHORT).show()
                            }
                        }

                        catch(v : VerifyError){
                            withContext(Dispatchers.Main){
                                Toast.makeText(context, v.localizedMessage, Toast.LENGTH_SHORT).show()
                            }

                        }

            }

        }
    }

    private fun emptyFields(){
        etFName.setText("")
        etLName.setText("")
        etEmail.setText("")
        etUsername.setText("")
        etPassword.setText("")
        etPassword2.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("etFName", etFName.text.toString())
        outState.putString("etLName", etLName.text.toString())
        outState.putString("etEmail", etEmail.text.toString())
        outState.putString("etUsername", etUsername.text.toString())
        outState.putString("etPassword", etPassword.text.toString())
        outState.putString("etPassword2", etPassword2.text.toString())
    }

    private fun saveState(_savedInstanceState : Bundle?){
        etFName.setText(_savedInstanceState?.getString("etFName"))
        etFName.setText(_savedInstanceState?.getString("etLName"))
        etFName.setText(_savedInstanceState?.getString("etEmail"))
        etFName.setText(_savedInstanceState?.getString("etUsername"))
        etFName.setText(_savedInstanceState?.getString("etPassword"))
        etFName.setText(_savedInstanceState?.getString("etPassword2"))
    }
}