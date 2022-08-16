package com.ewnbd.goh.repository.profilePicUpdate

import com.ewnbd.goh.data.model.response_all_class.profilePicUpdate.ProfilePicUpdateResponse
import com.ewnbd.goh.data.model.response_all_class.verification.VeificationResponse
import com.ewnbd.goh.utils.Resource
import okhttp3.MultipartBody
import java.io.File

interface ProfilePicUpdateRepository {
    suspend fun profilePicUpdateResponse(header: Map<String, String>
                                         ,dp: MultipartBody.Part): Resource<ProfilePicUpdateResponse>
}