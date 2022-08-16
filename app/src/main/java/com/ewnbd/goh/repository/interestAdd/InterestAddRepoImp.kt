package com.ewnbd.goh.repository.interestAdd

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.InterserAddRequest
import com.ewnbd.goh.data.model.response_all_class.interstAddResposne.InterestAddResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InterestAddRepoImp @Inject constructor(
    private val api: ApiInterface
): InterestAddRepository {
    override suspend fun interestAddResponse(
        header: Map<String, String>,
        userId: String,
        interserAddRequest: InterserAddRequest
    ): Resource<InterestAddResponse> {
        val response = try {
            api.addInterest(header,userId,interserAddRequest)
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