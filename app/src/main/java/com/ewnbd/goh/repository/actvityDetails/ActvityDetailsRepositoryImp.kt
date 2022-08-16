package com.ewnbd.goh.repository.actvityDetails

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.model.response_all_class.actvityDetails.ActvityDetailsResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ActvityDetailsRepositoryImp @Inject constructor(
    private val apiInterface: ApiInterface
): ActvityDetailsRepository {
    override suspend fun activityDetailsResponse(
        header: Map<String, String>,
        actvityId: String
    ): Resource<ActvityDetailsResponse> {
        val response = try {
            apiInterface.activityDetails(header,actvityId)
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