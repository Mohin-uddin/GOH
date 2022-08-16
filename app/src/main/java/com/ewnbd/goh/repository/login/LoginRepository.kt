package com.ewnbd.goh.repository.login


import com.ewnbd.goh.data.model.response_all_class.login.LoginResponse
import com.ewnbd.goh.utils.Resource


interface LoginRepository {
    suspend fun loginResponse(username: String,password: String): Resource<LoginResponse>
}