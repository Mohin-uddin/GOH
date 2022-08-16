package com.ewnbd.goh.repository.verification

import com.ewnbd.goh.data.model.response_all_class.verification.VeificationResponse
import com.ewnbd.goh.utils.Resource

interface VerificationRepository {
    suspend fun verificationResponse(userId: String,code:String): Resource<VeificationResponse>
}