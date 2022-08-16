package com.ewnbd.goh.data.model.response_all_class.friendRequestList

data class Result(
    val fr_sent_by: FrSentBy,
    val fr_sent_to: FrSentTo,
    val id: Int,
    val request_sent_time: String
)