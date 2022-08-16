package com.ewnbd.goh.repository.categories

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CategoryRepositoryImp @Inject constructor(
    private val api: ApiInterface
): CategoryRepository {
    override suspend fun categoryResponse(header: Map<String, String>): Resource<ActivityCategoryListResponse> {
        val response = try {
            api.categoryList(header)
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