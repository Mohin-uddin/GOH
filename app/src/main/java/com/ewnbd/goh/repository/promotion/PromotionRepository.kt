package com.ewnbd.goh.repository.promotion

import com.ewnbd.goh.data.model.request_all_class.PromotionRequest
import com.ewnbd.goh.data.model.response_all_class.profilePicUpdate.ProfilePicUpdateResponse
import com.ewnbd.goh.data.model.response_all_class.promotion.PromotionResponse
import com.ewnbd.goh.utils.Resource
import okhttp3.MultipartBody

interface PromotionRepository {
    suspend fun profilePicUpdateResponse(header: Map<String, String>
                                         ,promotionRequest: PromotionRequest): Resource<PromotionResponse>
}