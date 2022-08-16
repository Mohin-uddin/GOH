package com.ewnbd.goh.repository.sentFreindRequest

import com.ewnbd.goh.data.model.response_all_class.notification.SentFriendRequestResponse
import com.ewnbd.goh.utils.Resource

interface SentFriendRequestRepository {
    suspend fun sentFriendRequestRepo(header: Map<String, String>
                                      ,userId:String): Resource<SentFriendRequestResponse>
}