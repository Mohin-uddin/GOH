package com.ewnbd.goh.repository.activityList

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.activities.ActivitiesListResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ActivityListrepositoryImp @Inject constructor(
    private val api: ApiInterface
) : ActivityListRepository {

    override suspend fun activityListResponse(header: Map<String, String>): Resource<ActivitiesListResponse> {


        val response = try {
            api.activitiesList(header)
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