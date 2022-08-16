package com.ewnbd.goh.repository.profilePicUpdate

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.profilePicUpdate.ProfilePicUpdateResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class ProfilePicUpdateRepoImp @Inject constructor(
private val api: ApiInterface
): ProfilePicUpdateRepository {
    override suspend fun profilePicUpdateResponse(
        header: Map<String, String>,
        dp: MultipartBody.Part
    ): Resource<ProfilePicUpdateResponse> {
        val response = try {
            api.profilePicUpdate(header,dp)
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