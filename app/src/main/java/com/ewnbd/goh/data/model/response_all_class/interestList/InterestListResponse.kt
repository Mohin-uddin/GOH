package com.ewnbd.goh.data.model.response_all_class.interestList

data class InterestListResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)