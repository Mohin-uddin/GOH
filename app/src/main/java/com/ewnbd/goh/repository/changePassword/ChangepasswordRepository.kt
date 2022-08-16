package com.ewnbd.goh.repository.changePassword

import com.ewnbd.goh.data.model.request_all_class.ChangePasswordRequest
import com.ewnbd.goh.data.model.response_all_class.ForgetPasswordResponse
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.utils.Resource

interface ChangepasswordRepository {
    suspend fun changePasswordRepository(header: Map<String, String>
                                          ,changePassword:ChangePasswordRequest,userId: String): Resource<ForgetPasswordResponse>
}