package com.ewnbd.goh.repository.acceptFreindRequest

import com.ewnbd.goh.data.model.response_all_class.acceptRequest.AcceptRequestResponse
import com.ewnbd.goh.data.model.response_all_class.activities.ActivitiesListResponse
import com.ewnbd.goh.utils.Resource

interface AcceptFriendRequestRepo {
    suspend fun activityListResponse(header : Map<String,String>,userId: String): Resource<AcceptRequestResponse>
}