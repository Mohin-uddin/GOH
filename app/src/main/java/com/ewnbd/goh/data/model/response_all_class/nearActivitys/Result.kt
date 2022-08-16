package com.ewnbd.goh.data.model.response_all_class.nearActivitys

data class Result(
    val activity_date: String,
    val end_time: String,
    val start_time: String,
    val activity_time_id: String,
    val age_range: AgeRange,
    val age_range_id: String,
    val category: Any,
    val category_id: Any,
    val created: String,
    val creator: Creator,
    val desc: String,
    val gender: String,
    val id: Int,
    val status: Int,
    val latitude: Double,
    val longitude: Double,
    val min_participate: Int,
    val name: String,
    val photo: String,
    val place: String,
    val priority: Int,
    val privacy: String
)