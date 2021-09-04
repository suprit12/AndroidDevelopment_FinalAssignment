package com.suprit.hireaudit.api

import com.suprit.hireaudit.entities.User
import com.suprit.hireaudit.response.ImageResponse
import com.suprit.hireaudit.response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("register")
    suspend fun register(
            @Body user: User
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
            @Field("emailAddress") emailAddress : String,
            @Field("password") password : String
    ) : Response<LoginResponse>

    @Multipart
    @PUT("user/uploadImage/{id}")
    suspend fun uploadImage(
        @Header("Authorization") token:String,
        @Path("id") id:String,
        @Part file: MultipartBody.Part
    ):Response<ImageResponse>

}