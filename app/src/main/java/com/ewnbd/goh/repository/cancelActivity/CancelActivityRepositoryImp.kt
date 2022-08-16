package com.ewnbd.goh.repository.cancelActivity

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.cancelActivity.CancelActivityResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CancelActivityRepositoryImp @Inject constructor(
    private val api: ApiInterface
): CancelActivityRepository {
    override suspend fun cancelActivity(
        header: Map<String, String>,
        activtyId: String
    ): Resource<CancelActivityResponse> {
        val response = try {
            api.getActivityDeleteRequest(header,activtyId)
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