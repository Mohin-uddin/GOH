package com.ewnbd.goh.repository.friendRemove

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.friendRequestWithdraw.FriendRequestWithdrawResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FriendRemoveRepoImp @Inject constructor(
    private val api: ApiInterface
): FriendRemoveRepository {
    override suspend fun friendRemoveResponse(
        header: Map<String, String>,
        userId: String
    ): Resource<FriendRequestWithdrawResponse> {
        val response = try {
            api.getFriendRemove(header,userId)
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