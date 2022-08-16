package com.ewnbd.goh.repository.codeMtach

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.CodeMatchingRequest
import com.ewnbd.goh.data.model.response_all_class.codeMatch.CodeMatchResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CodeMatchingRepositoryImp @Inject constructor(
    private val api: ApiInterface
): CodeMatchRepository {
    override suspend fun codeMatchResponse(
        codeMatchingRequest: CodeMatchingRequest,
        mobileNumber: String
    ): Resource<CodeMatchResponse> {
        val response = try {
            api.codeMatch(codeMatchingRequest,mobileNumber)
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