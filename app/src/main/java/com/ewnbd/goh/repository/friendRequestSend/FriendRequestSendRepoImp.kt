package com.ewnbd.goh.repository.friendRequestSend

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.notification.SentFriendRequestResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FriendRequestSendRepoImp @Inject constructor(
    private val api: ApiInterface
): FriendRequestSendRepo {
    override suspend fun friendRequestSendResponse(
        header: Map<String, String>,
        userId: String
    ): Resource<SentFriendRequestResponse> {
        val response = try {
            api.getFriendRequest(header,userId)
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