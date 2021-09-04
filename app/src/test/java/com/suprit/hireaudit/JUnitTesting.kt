package com.suprit.hireaudit

import com.suprit.hireaudit.api.ServiceBuilder
import com.suprit.hireaudit.entities.HireModel
import com.suprit.hireaudit.entities.User
import com.suprit.hireaudit.repository.HireRepository
import com.suprit.hireaudit.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class JUnitTesting {
    private lateinit var userRepository: UserRepository
    private lateinit var hireRepository: HireRepository

    @Test
    fun checkSignUp() = runBlocking {
        val user = User(
            fName = "Deep",
            lName = "Khatiwada",
            emailAddress = "deeepeee@gmail.com",
            address = "Gothatar",
            dob = "2000-2-12",
            gender = "Male",
            organization = "Soft",
            mobileNo = "9876125523",
            password = "deep",
        )
        userRepository = UserRepository()
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }


    @Test
    fun checkLogin() = runBlocking {
        userRepository = UserRepository()
        val response = userRepository.loginUser("hardik@gmail.com", "asd")
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun hireAccountant() = runBlocking {
        userRepository = UserRepository()
        hireRepository = HireRepository()
        val hire =
            HireModel(endDate = "2021-04-21", userID = "607bb942f2f31b06ec3f5b0a")
        ServiceBuilder.token = userRepository.loginUser("hardik@gmail.com", "asd").token
        val expectedResult = true
        val actualResult = hireRepository.hireAccountant("60660f4d8a3fc619e005105a", hire).success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getMyAccountant() = runBlocking {
        userRepository = UserRepository()
            hireRepository = HireRepository()
        ServiceBuilder.token =userRepository.loginUser("hardik@gmail.com","asd").token
        ServiceBuilder.data?.get(0)?._id ="607bb942f2f31b06ec3f5b0a"
        val response = hireRepository.getMyAccountants()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }
    @Test
    fun deleteCart() = runBlocking {
        userRepository = UserRepository()
        hireRepository = HireRepository()
        ServiceBuilder.token =userRepository.loginUser("hardik@gmail.com","asd").token
        val expectedResult = true
        val response = hireRepository.deleteMyAccountant("607c04caca2d824398515ffc")
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }
    }

