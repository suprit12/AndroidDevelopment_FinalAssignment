package com.suprit.hireaudit

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.suprit.hireaudit.fragments.HomeFragment
import com.suprit.hireaudit.fragments.ProfileFragment
import com.suprit.hireaudit.fragments.WorkFragment

class DashboardActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager : SensorManager
    private var sensor : Sensor? = null

    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        if(!checkSensor()){
            return
        }

        else{
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        bottomNav = findViewById(R.id.bottomNav)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameContainer, HomeFragment())

            commit()
        }


        bottomNav.setOnNavigationItemSelectedListener{

            when(it.itemId) {

                R.id.home -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameContainer, HomeFragment())

                        addToBackStack(null)

                        commit()
                    }
                }

                R.id.progress -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameContainer, WorkFragment())

                        addToBackStack(null)

                        commit()
                    }
                }

                R.id.settings -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameContainer, ProfileFragment())

                        addToBackStack(null)
                        commit()
                    }
                }

            }
            true
        }


    }

    private fun checkSensor() : Boolean{
        var flag = true

        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null){
            flag = false
        }

        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]

        if(values < 3){
            logout()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun logout(){
        startActivity(Intent(this, LoginActivity::class.java))

        val preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }


}