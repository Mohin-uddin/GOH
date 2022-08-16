package com.ewnbd.goh.repository.codeMtach

import com.ewnbd.goh.data.model.request_all_class.CodeMatchingRequest
import com.ewnbd.goh.data.model.request_all_class.forget.ForgetpasswordRequest
import com.ewnbd.goh.data.model.response_all_class.ForgetPasswordResponse
import com.ewnbd.goh.data.model.response_all_class.codeMatch.CodeMatchResponse
import com.ewnbd.goh.utils.Resource

interface CodeMatchRepository {
    suspend fun codeMatchResponse(codeMatchingRequest: CodeMatchingRequest,mobileNumber: String):
            Resource<CodeMatchResponse>
}