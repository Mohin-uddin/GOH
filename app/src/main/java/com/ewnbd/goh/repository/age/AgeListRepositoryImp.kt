package com.ewnbd.goh.repository.age

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.age.AgeListResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AgeListRepositoryImp  @Inject constructor(
    private val api: ApiInterface
): AgeListRepository {
    override suspend fun ageListResponse(header: Map<String, String>): Resource<AgeListResponse> {
        val response = try {
            api.ageList(header)
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