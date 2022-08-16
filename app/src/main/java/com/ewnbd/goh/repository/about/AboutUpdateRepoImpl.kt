package com.ewnbd.goh.repository.about

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.AboutResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AboutUpdateRepoImpl @Inject constructor(
    private val api: ApiInterface
) : AboutUpdateRepository{
    override suspend fun aboutUpdateRes(
        header: Map<String, String>,
        aboutUpdate: AboutResponse
    ): Resource<AboutResponse> {
        val response = try {
            api.updateAbout(header,aboutUpdate)
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