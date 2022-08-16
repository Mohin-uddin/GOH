package com.ewnbd.goh.repository.nearActivity

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NearActivitiesListRepoImp @Inject constructor(
    private val api: ApiInterface
): NearActivitysListRepository {
    override suspend fun nearActivityResponse(
        header: Map<String, String>,
        latitude: String,
        longitude: String
    ): Resource<NearActivitiesResponse> {
        val response = try {
            api.getNearActivities(header)
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