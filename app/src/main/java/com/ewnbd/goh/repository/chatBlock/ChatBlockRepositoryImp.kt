package com.ewnbd.goh.repository.chatBlock

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.repository.activityShare.ChatBlockRepository
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChatBlockRepositoryImp  @Inject constructor(
    private val api: ApiInterface
): ChatBlockRepository {
    override suspend fun chatBlockResponse(
        header: Map<String, String>,
        userId: String
    ): Resource<ShareActivityResponse> {
        val response = try {
            api.chatBlock(header,userId)
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