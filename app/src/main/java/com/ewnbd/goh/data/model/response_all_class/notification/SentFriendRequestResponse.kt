package com.ewnbd.goh.data.model.response_all_class.notification

data class SentFriendRequestResponse(
    val fr_sent_by: FrSentBy,
    val fr_sent_to: FrSentTo,
    val id: Int,
    val request_sent_time: String
)