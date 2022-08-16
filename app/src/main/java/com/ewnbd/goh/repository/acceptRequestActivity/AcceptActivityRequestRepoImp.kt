package com.ewnbd.goh.repository.acceptRequestActivity

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.acceptActivityRequest.ActivityAcceptRequestResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AcceptActivityRequestRepoImp @Inject constructor(
    private val api: ApiInterface
):AcceptActivityRequestRepo {
    override suspend fun activityAcceptResponse(
        header: Map<String, String>,
        activityId: String,
        userId: String
    ): Resource<ActivityAcceptRequestResponse> {
        val response = try {
            api.acceptActivityRequest(header,activityId,userId)
        }catch (ioExeption: IOException){
            Log.e("CheckCall", "loginResponse: $ioExeption" )
            return Resource.Error(ioExeption.toString())

        }
        catch (e: HttpException){
            Log.e("CheckCall", "loginResponse hlw: "+e.response().toString()+"  asdad "+e.code())
            return Resource.Error(""+e.response()?.message())
        }
        Log.e("CheckCall", "loginResponse: ")
        return Resource.Success(response)
    }
}