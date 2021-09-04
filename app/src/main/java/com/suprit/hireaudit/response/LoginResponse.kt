package com.suprit.hireaudit.response

import com.suprit.hireaudit.entities.HireModel
import com.suprit.hireaudit.entities.User

data class LoginResponse (
        val token : String? = null,
        val success : Boolean? = null,
        val message : String? = null,
        val data : MutableList<User>? = null,
        val hireData : MutableList<HireModel>? = null
)