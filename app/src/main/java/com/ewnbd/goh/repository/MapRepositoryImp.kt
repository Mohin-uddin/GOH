package com.ewnbd.goh.repository

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.mapSearch.MapSearchResponse
import com.ewnbd.goh.data.model.response_all_class.verification.VeificationResponse
import com.ewnbd.goh.data.model.response_all_class.verification.VerifiactionRequest
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MapRepositoryImp @Inject constructor(
    private val api: ApiInterface
) : MapRepository{
    override suspend fun mapResponse(
        mapApi: String,
        searchData: String
    ): Resource<MapSearchResponse> {

        val response = try {
            api.mapApi("https://maps.googleapis.com/maps/api/place/autocomplete/json",searchData,mapApi)
        }catch (ioExeption: IOException){
            Log.e("CheckDefine", "loginResponse: $ioExeption" )
            return Resource.Error(ioExeption.toString())

        }
        catch (e: HttpException){
            Log.e("CheckDefine", "loginResponse hlw: "+e.response().toString()+"  asdad "+e.code())
            return Resource.Error(""+e.code())
        }
        Log.e("CheckDefine", "loginResponse: ")
        return Resource.Success(response)
    }
}