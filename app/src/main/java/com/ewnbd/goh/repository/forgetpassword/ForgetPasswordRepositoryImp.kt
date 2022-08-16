package com.ewnbd.goh.repository.forgetpassword

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.forget.ForgetpasswordRequest
import com.ewnbd.goh.data.model.response_all_class.ForgetPasswordResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ForgetPasswordRepositoryImp @Inject constructor(
    private val api: ApiInterface
): ForgetPasswordRepository {
    override suspend fun forgetPasswordResponse(forgetpasswordRequest: ForgetpasswordRequest,
                                                mobileNumber: String,code: String): Resource<ForgetPasswordResponse> {
        val response = try {
            api.forgetPassword(forgetpasswordRequest,mobileNumber,code)
        }catch (ioExeption: IOException){
            Log.e("CheckCall", "loginResponse: $ioExeption" )
            return Resource.Error(ioExeption.toString())

        }
        catch (e: HttpException){
            Log.e("CheckCall", "loginResponse hlw: "+e.response().toString()+"  asdad "+e.code())
            return Resource.Error(""+e.code())
        }
        Log.e("CheckCall", "loginResponse: ")
        return Resource.Success(response)
    }
}