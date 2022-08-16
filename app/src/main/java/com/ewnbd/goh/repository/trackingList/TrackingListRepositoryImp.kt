package com.ewnbd.goh.repository.trackingList

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.trackingissue.TrackingIssueListResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrackingListRepositoryImp @Inject constructor(
    private val api: ApiInterface
): TrackingListRepository {
    override suspend fun trackingListRequest(header: Map<String, String>): Resource<TrackingIssueListResponse> {
        val response = try {
            api.trackingIssue(header)
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