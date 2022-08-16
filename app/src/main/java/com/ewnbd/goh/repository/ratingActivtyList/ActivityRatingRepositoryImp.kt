package com.ewnbd.goh.repository.ratingActivtyList

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.activity_rating.ActivityRatingResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class ActivityRatingRepositoryImp @Inject constructor(
    private val api: ApiInterface
): ActvityRatingListRepository {
    override suspend fun ratingRepository(
        header: Map<String, String>,
        activtyId: String
    ): Resource<ActivityRatingResponse> {
        val response = try {
            api.ratingActivityList(header,activtyId)
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