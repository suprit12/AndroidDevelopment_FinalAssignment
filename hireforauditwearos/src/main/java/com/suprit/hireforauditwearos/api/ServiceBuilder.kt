package com.suprit.hireaudit.api

import com.suprit.hireaudit.entities.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "http://10.0.2.2:90/"
    var token : String ? = null
    var data : MutableList<User> ? = null
    private val okHttp = OkHttpClient.Builder()

    private val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

    private val retrofit = retrofitBuilder.build()

    fun <T> buildService(serviceType : Class<T>) : T {
        return retrofit.create(serviceType)
    }

//    fun loadImagePath() : String{
//        val arr = BASE_URL.split("/").toTypedArray()
//        return arr[0] + "/" + arr[1] + arr[2] + "/uploads"
//    }

    fun loadImagePath() : String {
        return BASE_URL
    }
}