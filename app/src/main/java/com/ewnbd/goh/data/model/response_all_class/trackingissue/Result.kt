package com.ewnbd.goh.data.model.response_all_class.trackingissue

data class Result(
    val category: Category,
    val category_id: String,
    val created: String,
    val creator: Creator,
    val desc: String,
    val id: Int,
    val status: String
)