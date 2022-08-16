package com.ewnbd.goh.repository.profile

import com.ewnbd.goh.data.model.response_all_class.profile.ProfileDetailsResponse
import com.ewnbd.goh.utils.Resource

interface ProfileRepository {
    suspend fun profileResponse(header: Map<String, String>
                                     ,userId:String): Resource<ProfileDetailsResponse>
}