package com.ewnbd.goh.repository.reportIssue

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.ReportIssueRequest
import com.ewnbd.goh.data.model.response_all_class.issue.IssueResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReportIssueRepositoryImp @Inject constructor(
    private val api: ApiInterface
): ReportissueRepository {
    override suspend fun reportIssueRequest(
        header: Map<String, String>,
        reportIssueRequest: ReportIssueRequest
    ): Resource<IssueResponse> {
        val response = try {
            api.reportIssue(header,reportIssueRequest)
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