package com.ewnbd.goh.repository.activityList

import com.ewnbd.goh.data.model.response_all_class.activities.ActivitiesListResponse
import com.ewnbd.goh.utils.Resource

interface ActivityListRepository {
    suspend fun activityListResponse(header : Map<String,String>): Resource<ActivitiesListResponse>
}