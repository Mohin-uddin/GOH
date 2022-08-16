package com.ewnbd.goh.repository.friendList

import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.data.model.response_all_class.friendList.FriendListResponse
import com.ewnbd.goh.utils.Resource

interface FriendListRepository {
    suspend fun friendListResponse(header: Map<String, String>,userId: String): Resource<FriendListResponse>
}