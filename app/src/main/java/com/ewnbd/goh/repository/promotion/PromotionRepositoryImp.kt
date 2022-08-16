package com.ewnbd.goh.repository.promotion

import android.util.Log
import com.ewnbd.goh.data.model.request_all_class.PromotionRequest
import com.ewnbd.goh.data.model.response_all_class.promotion.PromotionResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PromotionRepositoryImp @Inject constructor(
    private val api: ApiInterface
):PromotionRepository {
    override suspend fun profilePicUpdateResponse(
        header: Map<String, String>,
        promotionRequest: PromotionRequest,
    ): Resource<PromotionResponse> {
        val response = try {
            api.promotionRequest(header,promotionRequest,ConstValue.activityId)
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