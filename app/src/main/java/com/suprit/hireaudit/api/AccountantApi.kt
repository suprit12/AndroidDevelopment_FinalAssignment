package com.suprit.hireaudit.api

import com.suprit.hireaudit.entities.Accountant
import com.suprit.hireaudit.response.GetResponse
import com.suprit.hireaudit.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface AccountantApi {

    @Multipart
    @POST("addAccountant")
    suspend fun register(
        @Body accountant: Accountant
    ) : Response<LoginResponse>

    @GET("accountant/all")
    suspend fun getAccountants(
        @Header("Authorization") token : String
    ) : Response<GetResponse>




}