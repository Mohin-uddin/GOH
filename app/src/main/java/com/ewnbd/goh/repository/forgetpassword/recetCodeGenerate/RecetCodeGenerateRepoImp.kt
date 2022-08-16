package com.ewnbd.goh.repository.forgetpassword.recetCodeGenerate

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.ResetPassGenareteRequest
import com.ewnbd.goh.data.model.response_all_class.receatCode.RecetGenerateCodeResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RecetCodeGenerateRepoImp @Inject constructor(
    private val api: ApiInterface
): RecetCodeGenerateRepository {
    override suspend fun recetCodeResponse(username: String): Resource<RecetGenerateCodeResponse> {
        val response = try {
            val user = ResetPassGenareteRequest(username)
            api.resetPassCodeGenerate(user)
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