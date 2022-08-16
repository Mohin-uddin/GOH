package com.ewnbd.goh.data.model.response_all_class.activity_categorylist

data class ActivityCategoryListResponse(
    val count: Int,
    val next: Int,
    val previous: Int,
    val results: List<Result>
)