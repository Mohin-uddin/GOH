package com.ewnbd.goh.repository.friendRequestList

import com.ewnbd.goh.data.model.response_all_class.friendRequestList.FriendRequestListresponse
import com.ewnbd.goh.utils.Resource

interface FriendRequestListRepo {
    suspend fun friendRequestListResponse(header: Map<String,String>): Resource<FriendRequestListresponse>
}