package com.ewnbd.goh.data.model.response_all_class.organigationActivities

data class OrganizedActivitiesResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)