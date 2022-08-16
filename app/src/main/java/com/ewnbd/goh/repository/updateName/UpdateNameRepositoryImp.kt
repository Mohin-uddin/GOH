package com.ewnbd.goh.repository.updateName

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.UpdateNameRequest
import com.ewnbd.goh.data.model.response_all_class.name_update.NameUpdateResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateNameRepositoryImp@Inject constructor(
    private val api: ApiInterface
): UpdatenameRepository {
    override suspend fun updateNameRepo(header: Map<String, String>,updateNameRequest: UpdateNameRequest):
            Resource<NameUpdateResponse> {
        val response = try {
            api.nameUpdate(header,updateNameRequest)
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