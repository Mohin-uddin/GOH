package com.ewnbd.goh.repository.categoryWiseSearch

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CategoryWiseActivitySearchRepoImp @Inject constructor(
    private val api: ApiInterface
): CategoryWiseActivitySearchRepo {
    override suspend fun categorySearchRequestRepo(
        header: Map<String, String>,
        categoryId: String
    ): Resource<NearActivitiesResponse> {
        val response = try {
            api.getCategoryWiseNear(header,categoryId)
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