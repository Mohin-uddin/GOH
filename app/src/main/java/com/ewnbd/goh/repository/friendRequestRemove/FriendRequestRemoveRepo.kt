package com.ewnbd.goh.repository.friendRequestRemove

import com.ewnbd.goh.data.model.response_all_class.friendRequestWithdraw.FriendRequestWithdrawResponse
import com.ewnbd.goh.utils.Resource

interface FriendRequestRemoveRepo {
    suspend fun friendRequestRemoveResponse(header: Map<String, String>,userId: String): Resource<FriendRequestWithdrawResponse>
}