package com.ewnbd.goh.data.model.response_all_class.age

data class AgeListResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)