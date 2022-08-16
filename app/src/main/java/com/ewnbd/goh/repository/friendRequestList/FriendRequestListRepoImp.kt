package com.ewnbd.goh.repository.friendRequestList

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.friendRequestList.FriendRequestListresponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FriendRequestListRepoImp @Inject constructor(
    private val api:ApiInterface
): FriendRequestListRepo {
    override suspend fun friendRequestListResponse(header: Map<String, String>):
            Resource<FriendRequestListresponse> {
        val response = try {
            api.requestList(header)
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