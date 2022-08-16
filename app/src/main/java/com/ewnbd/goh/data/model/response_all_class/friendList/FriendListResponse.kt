package com.ewnbd.goh.data.model.response_all_class.friendList

data class FriendListResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)