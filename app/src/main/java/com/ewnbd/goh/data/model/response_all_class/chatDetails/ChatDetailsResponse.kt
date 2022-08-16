package com.ewnbd.goh.data.model.response_all_class.chatDetails

data class ChatDetailsResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)