package com.suprit.hireaudit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.suprit.hireaudit.entities.Accountant
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.entities.HireModel
import com.suprit.hireaudit.models.HireAccountantNotification
import com.suprit.hireaudit.repository.HireRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class ViewAccountantActivity : AppCompatActivity() {
    private lateinit var viewAccountantsLayout : LinearLayout
    private lateinit var accountantImage : ImageView
    private lateinit var accountantName : TextView
    private lateinit var accountantEmail : TextView
    private lateinit var accountantGender : TextView
    private lateinit var accountantExp : TextView
    private lateinit var accountantMobNo : TextView
    private lateinit var btnBook : Button
    private lateinit var etEndDate : EditText

    private var accountantID = ""
    private var _id = ""

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_accountant)

        viewAccountantsLayout = findViewById(R.id.viewAccountantsLayout)
        accountantImage = viewAccountantsLayout.findViewById(R.id.accountantImage)
        accountantName = viewAccountantsLayout.findViewById(R.id.accountantName)
        accountantEmail = viewAccountantsLayout.findViewById(R.id.accountantEmail)
        accountantGender = viewAccountantsLayout.findViewById(R.id.accountantGender)
        accountantExp = viewAccountantsLayout.findViewById(R.id.accountantExp)
        accountantMobNo = viewAccountantsLayout.findViewById(R.id.accountantMob)
        btnBook = viewAccountantsLayout.findViewById(R.id.btnBook)
        etEndDate = viewAccountantsLayout.findViewById(R.id.etEndDate)
        var img  = ServiceBuilder.loadImagePath()




        var intent = intent.getParcelableExtra<Accountant>("accountant")

        if (intent != null) {

            accountantID = intent._id.toString()
            img += intent.accountantImage.toString().split("\\")[1]
            accountantName.text = "${intent.accountantFName } ${intent.accountantLName}"
            accountantEmail.text = intent.accountantEmailAddress
            accountantGender.text = intent.gender
            accountantExp.text = intent.accountantExperience.toString() + " years of experience"


            _id = intent._id.toString()

            Glide.with(this).load(img).into(accountantImage)
        }

        etEndDate.setOnClickListener{
            loadCalender()
        }

        btnBook.setOnClickListener{
            hireAccountant()
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

    @SuppressLint("SimpleDateFormat")
    private fun hireAccountant(){

        var endDate = etEndDate.text.toString()
        var userID = ServiceBuilder.data!![0]._id


        var newContract = HireModel(endDate = endDate, userID = userID)

        if(TextUtils.isEmpty(etEndDate.text)){
            etEndDate.error = "Please enter a date"

            etEndDate.requestFocus()

            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                var hireRepository = HireRepository()

                val response = hireRepository.hireAccountant(accountantID, newContract)

                if(response.success == true){
                    withContext(Dispatchers.Main){
                        createNotification()
                        Toast.makeText(this@ViewAccountantActivity, "Successfully booked an accountant", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            catch (ex : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ViewAccountantActivity, ex.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createNotification(){
        val notificationManager = NotificationManagerCompat.from(this)

        val notificationChannel = HireAccountantNotification(this)

        notificationChannel.createNotification()

        val notification = NotificationCompat.Builder(this, notificationChannel.CHANNEL1)
                .setSmallIcon(R.drawable.work)
                .setContentTitle("Your bookings")
                .setContentText("You have successfully booked an accountant")
                .setColor(Color.BLUE).build()

        notificationManager.notify(1, notification)
    }


}