package com.ewnbd.goh.data.model.response_all_class.chat

data class Result(
    val block_status: BlockStatus,
    val changeable_timestamp: String,
    val id: Int,
    val last_message: LastMessage,
    val unseen_message_count: UnseenMessageCount,
    val user1: Int,
    val user2: Int
)