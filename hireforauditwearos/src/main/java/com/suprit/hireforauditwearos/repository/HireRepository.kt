package com.suprit.hireaudit.repository

import com.suprit.hireaudit.api.ApiRequest
import com.suprit.hireaudit.api.HireApi
import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.entities.HireModel
import com.suprit.hireaudit.response.LoginResponse

class HireRepository : ApiRequest() {
    val myApi = ServiceBuilder.buildService(HireApi::class.java)

    suspend fun hireAccountant(id: String, hireModel : HireModel) : LoginResponse{
        return apiRequest1 {
            myApi.hireAccountant(ServiceBuilder.token!!, id, hireModel)
        }
    }

    suspend fun getMyAccountants() : LoginResponse{
        return apiRequest1 {
            myApi.getAccountants(ServiceBuilder.token!!)
        }
    }

    suspend fun deleteMyAccountant(id : String) : LoginResponse{
        return apiRequest1 {
            myApi.deleteMyHiring(ServiceBuilder.token!!, id)
        }
    }
}