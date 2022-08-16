package com.ewnbd.goh.data.model.response_all_class.organizationRequestList

data class Activity(
    val activity_date: String,
    val activity_time: ActivityTime,
    val age_range: AgeRange,
    val category: Any,
    val created: String,
    val creator: Creator,
    val desc: String,
    val gender: String,
    val id: Int,
    val latitude: Any,
    val longitude: Any,
    val min_participate: Int,
    val name: String,
    val photo: String,
    val place: Any,
    val priority: Int,
    val privacy: String
)