package com.ewnbd.goh.repository.activityRequestCancel

import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.model.response_all_class.activityRequestSend.ActivtyRequestSendResponse
import com.ewnbd.goh.utils.Resource

interface ActivityRequestCancelRepository {
    suspend fun activityRequestDenyResponse(header : Map<String,String>,senderId:String,activityId: String): Resource<ShareActivityResponse>
}