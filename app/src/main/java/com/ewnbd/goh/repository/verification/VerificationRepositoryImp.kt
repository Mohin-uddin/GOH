package com.ewnbd.goh.repository.verification

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.verification.VeificationResponse
import com.ewnbd.goh.data.model.response_all_class.verification.VerifiactionRequest
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VerificationRepositoryImp @Inject constructor(
    private val api: ApiInterface
) : VerificationRepository {
    override suspend fun verificationResponse(
        userId: String,
        code: String
    ): Resource<VeificationResponse> {
        Log.e("CheckDefine", "loginResponse: $userId  $code " )
        val verfication = VerifiactionRequest(code)
        val response = try {
            api.getVerification(verfication,userId)
        }catch (ioExeption: IOException){
            Log.e("CheckDefine", "loginResponse: $ioExeption" )
            return Resource.Error(ioExeption.toString())

        }
        catch (e: HttpException){
            Log.e("CheckDefine", "loginResponse hlw: "+e.response().toString()+"  asdad "+e.code())
            return Resource.Error(""+e.code())
        }
        Log.e("CheckDefine", "loginResponse: ")
        return Resource.Success(response)
    }
}