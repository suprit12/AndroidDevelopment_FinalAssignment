package com.suprit.hireaudit.repositoryapi

import com.suprit.hireaudit.api.AccountantApi
import com.suprit.hireaudit.api.ApiRequest
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.entities.Accountant
import com.suprit.hireaudit.response.GetResponse
import com.suprit.hireaudit.response.LoginResponse

class AccountantRepository : ApiRequest() {
    val myApi = ServiceBuilder.buildService(AccountantApi::class.java)

    suspend fun registerAccountant(accountant : Accountant) : LoginResponse{
        return apiRequest1 {
            myApi.register(accountant)
        }
    }

    suspend fun getAccountants() : GetResponse{
        return apiRequest1 {
            myApi.getAccountants(ServiceBuilder.token!!)
        }
    }

//    suspend fun uploadImage(accountant: Accountant, image : MultipartBody.Part) : LoginResponse{
//        return apiRequest1 {
//            myApi.register(accountant, image)
//        }
//    }
}