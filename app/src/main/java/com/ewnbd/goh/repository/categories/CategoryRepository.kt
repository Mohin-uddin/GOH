package com.ewnbd.goh.repository.categories

import com.ewnbd.goh.data.model.request_all_class.basic_profile.BasicProfileRequest
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.data.model.response_all_class.basicProfile.BasicProfileResponse
import com.ewnbd.goh.utils.Resource

interface CategoryRepository {
    suspend fun categoryResponse(header: Map<String, String>): Resource<ActivityCategoryListResponse>
}