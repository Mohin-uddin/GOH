package com.ewnbd.goh.data.model.response_all_class.timeListResponse

data class TimeListResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)