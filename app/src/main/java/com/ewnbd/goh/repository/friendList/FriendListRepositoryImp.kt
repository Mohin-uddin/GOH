package com.ewnbd.goh.repository.friendList

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.friendList.FriendListResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FriendListRepositoryImp  @Inject constructor(
    private val api: ApiInterface
): FriendListRepository {
    override suspend fun friendListResponse(header: Map<String, String>,userId: String): Resource<FriendListResponse> {
        val response = try {
            api.getFriendLIst(header,userId)
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