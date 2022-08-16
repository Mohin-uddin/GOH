package com.ewnbd.goh.repository.withdrawActivityRequest

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.WithdrawResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WithdrawRequestRepoImp @Inject constructor(
    private val api: ApiInterface
): WithdrawActivityRequest  {
    override suspend fun withDrawRequestResponse(
        header: Map<String, String>,
        activtyId: String
    ): Resource<WithdrawResponse> {

        val response = try {
            api.withDrawRequest(header,activtyId)
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