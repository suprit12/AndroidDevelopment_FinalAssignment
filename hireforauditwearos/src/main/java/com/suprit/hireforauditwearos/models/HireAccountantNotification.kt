package com.suprit.hireaudit.models

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class HireAccountantNotification(val context: Context){
    val CHANNEL1 : String = "Channel1"
    val CHANNEL2 : String = "Channel2"

    fun createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(CHANNEL1, "Channel1", NotificationManager.IMPORTANCE_HIGH)

            channel1.description = "Successfully hired"

            val channel2 = NotificationChannel(CHANNEL2, "Channel2", NotificationManager.IMPORTANCE_LOW)

            channel2.description = "Hired successfully"

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }


    }
}