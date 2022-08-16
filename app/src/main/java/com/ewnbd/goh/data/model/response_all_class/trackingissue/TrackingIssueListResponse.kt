package com.ewnbd.goh.data.model.response_all_class.trackingissue

data class TrackingIssueListResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)