package com.ewnbd.goh.repository.activityRequestSent

import com.ewnbd.goh.data.model.response_all_class.activities.ActivitiesListResponse
import com.ewnbd.goh.data.model.response_all_class.activityRequestSend.ActivtyRequestSendResponse
import com.ewnbd.goh.utils.Resource

interface ActivitySentRequestRepository {
    suspend fun activitySendRequestResponse(header : Map<String,String>,activityId:String): Resource<ActivtyRequestSendResponse>
}