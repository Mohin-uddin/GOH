package com.ewnbd.goh.repository.signup

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.signup.SignUpRequest
import com.ewnbd.goh.data.model.response_all_class.signup.SignUpResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import kotlinx.coroutines.*
import retrofit2.*
import java.io.IOException
import javax.inject.Inject

class SignUpRepositoryImp @Inject constructor(
    private val api: ApiInterface
) : SignUpRepository {
    private var errorResponse: String =""
        lateinit var successResponse: Response<SignUpResponse>

    override suspend fun signUpResponse(
        username: String,
        phoneNumber: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Resource<SignUpResponse?> {
        Log.e("CheckCall", "loginResponse: $username  $password " )
        val signUpRequest = SignUpRequest(phoneNumber,email,username,password,confirmPassword)
        val response =
            api.createUser(signUpRequest)

        Log.e("CheckCall", "loginResponse: ")
           response.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    successResponse = response
                    if (response.code() == 200) {
                       Resource.Success(response)
                    }
                    else{
                        errorResponse = response.errorBody()?.string()?:""
                        Resource.Error(response.errorBody()?.string()?:"")
                    }
                }
                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Log.e("chekingError", "Failer: ")
                     Resource.Success(call.request())
                }
            })

        runBlocking {
            delay(1500)
        }
        if (::successResponse.isInitialized&&successResponse.isSuccessful) {
            Log.e("Success", "signUpResponse: 1", )
            return Resource.Success(successResponse.body())
        }
        else if (::successResponse.isInitialized)
        {
            var errorSms= ""
            Log.e("Success", "signUpResponse: 2 ${errorResponse}", )
            if (errorResponse.contains("Enter a valid email address.")){
                errorSms+="Enter a valid email address.\n"
            }
            if(errorResponse.contains("user with this Email already exists.")){
                errorSms +="user with this Email already exists.\n"
            }
            if(errorResponse.contains("user with this Phone Number already exists.")){
                errorSms+="user with this Phone Number already exists."
            }

            return Resource.Error(errorSms)
        }
        else{
            Log.e("Success", "signUpResponse: 3", )
            return Resource.Success(null)
        }
    }

}