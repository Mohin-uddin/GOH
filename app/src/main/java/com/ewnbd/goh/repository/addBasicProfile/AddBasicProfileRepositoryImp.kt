package com.ewnbd.goh.repository.addBasicProfile

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.basic_profile.BasicProfileRequest
import com.ewnbd.goh.data.model.response_all_class.basicProfile.BasicProfileResponse
import com.ewnbd.goh.data.model.response_all_class.login.LoginRequest
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddBasicProfileRepositoryImp @Inject constructor(
    private val api: ApiInterface
): AddBasicProfileRepository {
    override suspend fun addBasicProfileResponse(header: String,
                                                 basicProfileRequest: BasicProfileRequest): Resource<BasicProfileResponse> {
        val response = try {
            api.addBasicProfile(header,basicProfileRequest)
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