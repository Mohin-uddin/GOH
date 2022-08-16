package com.ewnbd.goh.repository.actvityDetails

import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.model.response_all_class.actvityDetails.ActvityDetailsResponse
import com.ewnbd.goh.utils.Resource

interface ActvityDetailsRepository {
    suspend fun activityDetailsResponse(header : Map<String,String>,actvityId:String): Resource<ActvityDetailsResponse>
}