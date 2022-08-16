package com.ewnbd.goh.repository.activityFilter


import com.ewnbd.goh.data.model.request_all_class.ActivityFilterRequest
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.utils.Resource

interface ActivityFilterRepository {
    suspend fun activityFilterResponse(header : Map<String,String>,
                                       activityFilterRequest: ActivityFilterRequest): Resource<NearActivitiesResponse>
}