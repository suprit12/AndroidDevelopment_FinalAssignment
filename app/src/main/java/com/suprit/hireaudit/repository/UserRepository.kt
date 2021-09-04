package com.suprit.hireaudit.repository

import com.suprit.hireaudit.api.ApiRequest
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.api.UserApi
import com.suprit.hireaudit.entities.User
import com.suprit.hireaudit.response.ImageResponse
import com.suprit.hireaudit.response.LoginResponse
import okhttp3.MultipartBody

class UserRepository : ApiRequest() {
    val myApi = ServiceBuilder.buildService(UserApi::class.java)

    suspend fun registerUser(user: User): LoginResponse {
        return apiRequest1 {
            myApi.register(user)
        }
    }

    suspend fun loginUser(email : String, password : String) : LoginResponse {
        return apiRequest1 {
            myApi.login(email, password)
        }
    }

    suspend fun uploadImage(id : String, body: MultipartBody.Part) : ImageResponse{
        return apiRequest1 {
            myApi.uploadImage(ServiceBuilder.token!!, id,body)
        }
    }


}

