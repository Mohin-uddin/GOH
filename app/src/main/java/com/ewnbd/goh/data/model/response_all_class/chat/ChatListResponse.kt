package com.ewnbd.goh.data.model.response_all_class.chat

data class ChatListResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)