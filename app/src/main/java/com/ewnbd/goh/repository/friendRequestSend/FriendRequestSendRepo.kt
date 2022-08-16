package com.ewnbd.goh.repository.friendRequestSend

import com.ewnbd.goh.data.model.response_all_class.notification.SentFriendRequestResponse
import com.ewnbd.goh.utils.Resource

interface FriendRequestSendRepo {
    suspend fun friendRequestSendResponse(header: Map<String, String>,userId: String): Resource<SentFriendRequestResponse>
}