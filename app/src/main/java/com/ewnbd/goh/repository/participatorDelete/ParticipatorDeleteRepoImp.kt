package com.ewnbd.goh.repository.participatorDelete

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ParticipatorDeleteRepoImp @Inject constructor(
    private val api: ApiInterface
) : ParticipatorDeleteRepository {
    override suspend fun participatorResponse(
        header: Map<String, String>,
        participator: String,
        activityID: String
    ): Resource<ShareActivityResponse> {
        val response = try {
            api.participatorRemove(header,participator,activityID)
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