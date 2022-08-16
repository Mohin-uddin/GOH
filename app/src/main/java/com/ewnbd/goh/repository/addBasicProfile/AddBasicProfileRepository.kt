package com.ewnbd.goh.repository.addBasicProfile

import com.ewnbd.goh.data.model.request_all_class.basic_profile.BasicProfileRequest
import com.ewnbd.goh.data.model.response_all_class.basicProfile.BasicProfileResponse
import com.ewnbd.goh.utils.Resource

interface AddBasicProfileRepository {
    suspend fun addBasicProfileResponse(header: String,
                                        basicProfileRequest: BasicProfileRequest): Resource<BasicProfileResponse>
}