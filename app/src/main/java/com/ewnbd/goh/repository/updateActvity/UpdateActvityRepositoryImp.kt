package com.ewnbd.goh.repository.updateActvity

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.createActivty.CreateActivityResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class UpdateActvityRepositoryImp constructor(
    private val api: ApiInterface
): UpdateActvityRepository {
    override suspend fun updateActivityResponse(
        header: Map<String, String>,
        dp: MultipartBody.Part,
        name: RequestBody,
        desc: RequestBody,
        min_participate: RequestBody,
        gender: RequestBody,
        privacy: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        place: RequestBody,
        activity_date: RequestBody,
        category_id: RequestBody,
        age_range_id: RequestBody,
        start_time: RequestBody,
        end_time: RequestBody,
        userId: String
    ): Resource<CreateActivityResponse> {
        val response = try {
            api.activityUpdate(header,dp,name,desc,min_participate, gender, privacy, latitude,
                longitude, place, activity_date, category_id, age_range_id, start_time,end_time, userId)
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