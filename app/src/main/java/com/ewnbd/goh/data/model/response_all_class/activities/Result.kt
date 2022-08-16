package com.ewnbd.goh.data.model.response_all_class.activities

data class Result(
    val activity_date: String,
    val activity_time: ActivityTime,
    val activity_time_id: String,
    val age_range: AgeRange,
    val age_range_id: String,
    val category: Category,
    val category_id: String,
    val created: String,
    val creator: Creator,
    val desc: String,
    val gender: String,
    val id: Int,
    val lat: Double,
    val lng: Double,
    val min_participate: Int,
    val name: String,
    val photo: String,
    val place: String,
    val priority: Int,
    val privacy: String
)