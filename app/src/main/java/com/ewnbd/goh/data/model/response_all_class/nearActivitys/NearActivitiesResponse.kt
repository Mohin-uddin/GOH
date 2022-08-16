package com.ewnbd.goh.data.model.response_all_class.nearActivitys

data class NearActivitiesResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)