package com.ewnbd.goh.repository.activityFilter

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.ActivityFilterRequest
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ActivityFilterRepositoryImp @Inject constructor(
    private val api: ApiInterface
): ActivityFilterRepository {
    override suspend fun activityFilterResponse(
        header: Map<String, String>,
        activityFilterRequest: ActivityFilterRequest
    ): Resource<NearActivitiesResponse> {

        val response = try {
            Log.e("CheckingRequest", "activityFilterResponse: $header  $activityFilterRequest", )
            api.filterActivity(header,activityFilterRequest.category_id.toString(),activityFilterRequest.gender,
            activityFilterRequest.activity_date,activityFilterRequest.latitude,activityFilterRequest.longitude,activityFilterRequest.age_range_id,activityFilterRequest.activity_time_id)
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