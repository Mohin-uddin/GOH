package com.ewnbd.goh.repository.age

import com.ewnbd.goh.data.model.request_all_class.basic_profile.BasicProfileRequest
import com.ewnbd.goh.data.model.response_all_class.age.AgeListResponse
import com.ewnbd.goh.data.model.response_all_class.basicProfile.BasicProfileResponse
import com.ewnbd.goh.utils.Resource

interface AgeListRepository {
    suspend fun ageListResponse(header: Map<String, String>
    ): Resource<AgeListResponse>
}