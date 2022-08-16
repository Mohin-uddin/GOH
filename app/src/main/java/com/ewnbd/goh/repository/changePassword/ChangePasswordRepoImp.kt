package com.ewnbd.goh.repository.changePassword

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.ChangePasswordRequest
import com.ewnbd.goh.data.model.response_all_class.ForgetPasswordResponse
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChangePasswordRepoImp @Inject constructor(
    private val api: ApiInterface
): ChangepasswordRepository {
    override suspend fun changePasswordRepository(
        header: Map<String, String>,
        changePassword: ChangePasswordRequest,
        userId: String
    ): Resource<ForgetPasswordResponse> {
        val response = try {
            api.changePassword(header,changePassword,userId)
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