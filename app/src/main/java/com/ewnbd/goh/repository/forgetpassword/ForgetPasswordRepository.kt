package com.ewnbd.goh.repository.forgetpassword

import com.ewnbd.goh.data.model.request_all_class.forget.ForgetpasswordRequest
import com.ewnbd.goh.data.model.response_all_class.ForgetPasswordResponse
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.utils.Resource

interface ForgetPasswordRepository {
    suspend fun forgetPasswordResponse(forgetpasswordRequest: ForgetpasswordRequest,
                                       newPassword: String,confirmPass: String): Resource<ForgetPasswordResponse>
}