package com.suprit.hireaudit.api

import com.suprit.hireaudit.entities.HireModel
import com.suprit.hireaudit.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface HireApi {
    @POST("user/hireAccountant/{id}")
    suspend fun hireAccountant(
            @Header("Authorization") token : String,
            @Path("id") id : String,
            @Body hireData : HireModel

    ) : Response<LoginResponse>


    @GET("user/getMyAccountants")
    suspend fun getAccountants(
            @Header("Authorization") token: String

    ) : Response<LoginResponse>


    @DELETE("user/deleteBooking/{id}")
    suspend fun deleteMyHiring(
        @Header("Authorization") token: String,
        @Path("id") id : String
    ) : Response<LoginResponse>

    @PUT("user/updateTerms/{id}")
    suspend fun updateMyTerms(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body hireModel: HireModel
    ) : Response<LoginResponse>
}