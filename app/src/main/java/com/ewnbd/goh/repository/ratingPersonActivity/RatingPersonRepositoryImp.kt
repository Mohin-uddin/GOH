package com.ewnbd.goh.repository.ratingPersonActivity

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.RatingActivityRequest
import com.ewnbd.goh.data.model.response_all_class.RatingPersonResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RatingPersonRepositoryImp @Inject constructor(
    private val api: ApiInterface
): RatingRatingActivityRepository {
    override suspend fun ratingPersonRepository(
        header: Map<String, String>,
        activtyId: String,
        ratingActivityRequest: RatingActivityRequest
    ): Resource<RatingPersonResponse> {
        val response = try {
            api.ratingActivity(header,activtyId,ratingActivityRequest)
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