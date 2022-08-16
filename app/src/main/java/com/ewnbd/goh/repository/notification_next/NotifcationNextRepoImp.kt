package com.ewnbd.goh.repository.notification_next

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.notificationRe.NotifcationResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NotifcationNextRepoImp @Inject constructor(
    private val api: ApiInterface
) : NotificationNextRepository{
    override suspend fun notificationResponse(
        header: Map<String, String>,
        url: String
    ): Resource<NotifcationResponse> {
        val response = try {
            api.notificationNextPage(header,url)
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