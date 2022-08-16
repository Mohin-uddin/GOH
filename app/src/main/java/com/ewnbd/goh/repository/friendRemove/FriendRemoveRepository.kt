package com.ewnbd.goh.repository.friendRemove

import com.ewnbd.goh.data.model.response_all_class.friendList.FriendListResponse
import com.ewnbd.goh.data.model.response_all_class.friendRequestWithdraw.FriendRequestWithdrawResponse
import com.ewnbd.goh.utils.Resource

interface FriendRemoveRepository  {
    suspend fun friendRemoveResponse(header: Map<String, String>,userId: String): Resource<FriendRequestWithdrawResponse>
}