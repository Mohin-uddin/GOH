package com.ewnbd.goh.repository.forgetpassword.recetCodeGenerate

import com.ewnbd.goh.data.model.request_all_class.forget.ForgetpasswordRequest
import com.ewnbd.goh.data.model.response_all_class.ForgetPasswordResponse
import com.ewnbd.goh.data.model.response_all_class.receatCode.RecetGenerateCodeResponse
import com.ewnbd.goh.utils.Resource

interface RecetCodeGenerateRepository {
    suspend fun recetCodeResponse( username: String): Resource<RecetGenerateCodeResponse>
}