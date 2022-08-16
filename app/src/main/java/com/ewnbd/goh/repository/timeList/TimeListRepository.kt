package com.ewnbd.goh.repository.timeList

import com.ewnbd.goh.data.model.response_all_class.activities.ActivitiesListResponse
import com.ewnbd.goh.data.model.response_all_class.timeListResponse.TimeListResponse
import com.ewnbd.goh.utils.Resource

interface TimeListRepository {
    suspend fun timeListResponse(header : Map<String,String>): Resource<TimeListResponse>
}