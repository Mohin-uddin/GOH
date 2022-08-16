package com.ewnbd.goh.data.model.response_all_class.friendRequestList

data class FriendRequestListresponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)