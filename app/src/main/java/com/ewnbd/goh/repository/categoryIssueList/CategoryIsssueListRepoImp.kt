package com.ewnbd.goh.repository.categoryIssueList

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.CategoryIssueListResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CategoryIsssueListRepoImp  @Inject constructor(
    private val api: ApiInterface
): CategoryIssueListRepository {
    override suspend fun categoryIssueResponse(header: Map<String, String>): Resource<CategoryIssueListResponse> {
        val response = try {
            api.categoryIssueList(header)
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