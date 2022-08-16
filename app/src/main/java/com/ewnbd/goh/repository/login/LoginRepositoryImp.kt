package com.ewnbd.goh.repository.login

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.login.LoginRequest
import com.ewnbd.goh.data.model.response_all_class.login.LoginResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(
    private val api: ApiInterface
) : LoginRepository {

    override suspend fun loginResponse(
        username: String,
        password: String
    ): Resource<LoginResponse> {
        Log.e("CheckCall", "loginResponse: $username  $password " )
        val loginRequest = LoginRequest(username,password)
        val response = try {
            api.getLogin(loginRequest)
        }catch (ioExeption: IOException){
            Log.e("CheckCall", "loginResponse: $ioExeption" )
            return Resource.Error(ioExeption.toString())

        }
        catch (e:HttpException){
            Log.e("CheckCall", "loginResponse hlw: "+e.response().toString()+"  asdad "+e.code())
            return Resource.Error(""+e.code())
        }
        Log.e("CheckCall", "loginResponse: ")


        return Resource.Success(response)
    }



}