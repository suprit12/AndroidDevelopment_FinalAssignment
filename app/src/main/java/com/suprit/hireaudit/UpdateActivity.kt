package com.suprit.hireaudit

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.entities.HireModel
import com.suprit.hireaudit.repository.HireRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class UpdateActivity : AppCompatActivity() {
    private lateinit var updateLayout : LinearLayout
    private lateinit var etEndDate : EditText
    private lateinit var btnUpdate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        updateLayout = findViewById(R.id.updateLayout)

        etEndDate = updateLayout.findViewById(R.id.etEndDate)

        btnUpdate = updateLayout.findViewById(R.id.btnUpdate)

        val intent = intent.getParcelableExtra<HireModel>("newHirings")

        if (intent != null) {
            etEndDate.setText(intent.endDate!!.split("T")[0])
        }

        etEndDate.setOnClickListener{
            loadCalender()
        }

        btnUpdate.setOnClickListener{
            val hireRepository = HireRepository()
            var userID = ServiceBuilder.data!![0]._id
            var hireModel = HireModel(endDate = etEndDate.text.toString())

            CoroutineScope(Dispatchers.IO).launch {

                val response = userID?.let { it1 -> hireRepository.updateMyData(it1, hireModel) }

                if(response!!.success == true){
                    withContext(Main){
                        Toast.makeText(this@UpdateActivity, "Updated the date", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@UpdateActivity, DashboardActivity::class.java)

                        startActivity(intent)

                        (this@UpdateActivity as Activity).finish()
                    }
                }


            }
        }


    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadCalender(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                view, year, month, dayOfMonth ->
            etEndDate.setText("$year/${month + 1}/$dayOfMonth")
        }, year,month, day
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datePickerDialog.show()
    }
}