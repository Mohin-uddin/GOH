package com.ewnbd.goh.repository.organizationParticipetor

import android.util.Log
import com.ewnbd.goh.data.model.response_all_class.acceptActivityRequest.ActivityAcceptRequestResponse
import com.ewnbd.goh.data.model.response_all_class.organizationParticipetorList.OrganizationParticipetorListResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class OrganizationParticipetorListRepoImpl @Inject constructor(
    private val api: ApiInterface
): OrganizationParticipetorRepo {
    override suspend fun organizationParticipetorRepo(
        header: Map<String, String>,
        userId: String
    ): Resource<OrganizationParticipetorListResponse> {
        val response = try {
            api.getOrganizedParticipators(header,userId)
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