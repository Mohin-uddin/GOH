package com.ewnbd.goh.repository.activityShare

import com.ewnbd.goh.data.model.response_all_class.ShareActivityResponse
import com.ewnbd.goh.data.model.response_all_class.activityRequestSend.ActivtyRequestSendResponse
import com.ewnbd.goh.utils.Resource

interface ActivityShareRepository {
    suspend fun activityShareResponse(header : Map<String,String>,userId:String): Resource<ShareActivityResponse>
}