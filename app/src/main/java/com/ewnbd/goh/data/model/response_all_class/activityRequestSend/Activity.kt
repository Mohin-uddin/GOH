package com.ewnbd.goh.data.model.response_all_class.activityRequestSend

data class Activity(
    val activity_date: String,
    val activity_time: ActivityTime,
    val age_range: AgeRange,
    val category: Category,
    val created: String,
    val creator: Creator,
    val desc: String,
    val gender: String,
    val id: Int,
    val latitude: String,
    val longitude: String,
    val min_participate: Int,
    val name: String,
    val photo: String,
    val place: String,
    val priority: Int,
    val privacy: String
)