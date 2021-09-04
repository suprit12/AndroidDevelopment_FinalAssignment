package com.suprit.hireaudit.response

import com.suprit.hireaudit.entities.Accountant

data class GetResponse(
        val data : ArrayList<Accountant>? = null,
        val success : Boolean? = null
)