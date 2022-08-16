package com.ewnbd.goh.data.model.response_all_class.activities

data class ActivitiesListResponse(
    val count: Int,
    val next: Int,
    val previous: Int,
    val results: List<Result>
)