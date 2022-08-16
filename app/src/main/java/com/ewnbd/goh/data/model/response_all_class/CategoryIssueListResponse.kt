package com.ewnbd.goh.data.model.response_all_class

data class CategoryIssueListResponse(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)