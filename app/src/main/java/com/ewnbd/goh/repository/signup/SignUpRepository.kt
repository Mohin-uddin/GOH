package com.ewnbd.goh.repository.signup

import com.ewnbd.goh.data.model.response_all_class.signup.SignUpResponse
import com.ewnbd.goh.utils.Resource

interface SignUpRepository {
    suspend fun signUpResponse(username: String,phoneNumber:String,email:String,password: String,
                               confirmPassword: String): Resource<SignUpResponse?>
}