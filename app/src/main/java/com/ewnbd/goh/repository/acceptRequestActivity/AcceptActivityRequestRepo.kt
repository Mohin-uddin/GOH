package com.ewnbd.goh.repository.acceptRequestActivity

import com.ewnbd.goh.data.model.response_all_class.acceptActivityRequest.ActivityAcceptRequestResponse
import com.ewnbd.goh.data.model.response_all_class.acceptRequest.AcceptRequestResponse
import com.ewnbd.goh.utils.Resource

interface AcceptActivityRequestRepo {
    suspend fun activityAcceptResponse(header : Map<String,String>,
                                       activityId: String,userId: String): Resource<ActivityAcceptRequestResponse>
}